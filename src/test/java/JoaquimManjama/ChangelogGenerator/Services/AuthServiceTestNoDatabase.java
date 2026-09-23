package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import JoaquimManjama.ChangelogGenerator.Security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTestNoDatabase {

    private AuthService authService;
    private UserRepository repository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        jwtUtil = mock(JwtUtil.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authService = new AuthService();
        ReflectionTestUtils.setField(authService, "repository", repository);
        ReflectionTestUtils.setField(authService, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(authService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void testRegister_Success() {
        // Given
        RegisterRequestDTO request = new RegisterRequestDTO("New", "User", "new@example.com", "pass123");
        String encryptedPassword = "$2a$10$encryptedPassword";
        User newUser = new User("new@example.com", encryptedPassword, "New", "User");

        when(repository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass123")).thenReturn(encryptedPassword);
        when(repository.save(any(User.class))).thenReturn(newUser);
        when(jwtUtil.generateToken("new@example.com")).thenReturn("generated-token");

        // When
        String token = authService.register(request);

        // Then
        assertNotNull(token);
        assertEquals("generated-token", token);
        verify(repository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("pass123");
    }

    @Test
    void testRegister_DuplicateEmail() {
        // Given
        RegisterRequestDTO request = new RegisterRequestDTO("Another", "User", "existing@example.com", "pass123");
        User existingUser = new User("existing@example.com", "$2a$10$pass", "Existing", "User");

        when(repository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUser));

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(request));
        assertEquals("User already exists!", exception.getMessage());
    }

    @Test
    void testLogin_Success() {
        // Given
        String encryptedPassword = "$2a$10$encryptedPassword";
        User user = new User("user@example.com", encryptedPassword, "Test", "User");
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@example.com", "correctPassword");

        when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("correctPassword", encryptedPassword)).thenReturn(true);
        when(jwtUtil.generateToken("user@example.com")).thenReturn("login-token");

        // When
        String token = authService.login(loginRequest);

        // Then
        assertNotNull(token);
        assertEquals("login-token", token);
    }

    @Test
    void testLogin_WrongPassword() {
        // Given
        String encryptedPassword = "$2a$10$encryptedPassword";
        User user = new User("user@example.com", encryptedPassword, "Test", "User");
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@example.com", "wrongPassword");

        when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", encryptedPassword)).thenReturn(false);

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        assertEquals("Password is incorrect!", exception.getMessage());
    }

    @Test
    void testLogin_UserNotFound() {
        // Given
        LoginRequestDTO loginRequest = new LoginRequestDTO("nonexistent@example.com", "password");

        when(repository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When/Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        assertEquals("User not found!", exception.getMessage());
    }
}
