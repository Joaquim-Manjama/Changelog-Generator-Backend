package JoaquimManjama.ChangelogGenerator;

import JoaquimManjama.ChangelogGenerator.DTOs.AuthResponseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import JoaquimManjama.ChangelogGenerator.Services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Rollback database changes after each test
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        System.out.println("=== SETUP START ===");
        System.out.println("UserRepository: " + userRepository);
        System.out.println("AuthService: " + authService);
        System.out.println("PasswordEncoder: " + passwordEncoder);

        if (userRepository != null) {
            userRepository.deleteAll();
            System.out.println("Database cleaned");
        } else {
            System.out.println("ERROR: UserRepository is NULL!");
        }
        System.out.println("=== SETUP END ===");
    }

    @Test
    void testRegister_Success() {
        // Given: Registration request
        RegisterRequestDTO request = new RegisterRequestDTO("New", "user", "newuser@example.com", "password123");

        // When: Register user
        String token = authService.register(request);
        AuthResponseDTO response = new AuthResponseDTO(token, request.email());

        // Then: Should return valid response
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.token(), "Token should not be null");
        assertEquals("newuser@example.com", response.email());

        // Verify user is saved in database
        User savedUser = userRepository.findByEmail("newuser@example.com").orElse(null);
        assertNotNull(savedUser, "User should be saved in database");
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword()),
                "Password should be encrypted");
    }

    @Test
    void testRegister_DuplicateEmail() {
        // Given: Existing user
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");
        existingUser.setPassword(passwordEncoder.encode("pass123"));
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        userRepository.save(existingUser);

        // When: Try to register with same email
        RegisterRequestDTO request = new RegisterRequestDTO("Another", "User", "existing@example.com", "newpass");

        // Then: Should throw exception
        assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        }, "Should throw exception for duplicate email");
    }

    @Test
    void testLogin_Success() {
        // Given: Existing user
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("correctpassword"));
        user.setFirstName("Test");
        user.setLastName("User");
        userRepository.save(user);

        // When: Login with correct credentials
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@example.com", "correctpassword");

        String token = authService.login(loginRequest);
        AuthResponseDTO response = new AuthResponseDTO(token, loginRequest.email());

        // Then: Should return valid response
        assertNotNull(response);
        assertNotNull(response.token());
        assertEquals("user@example.com", response.email());
    }

    @Test
    void testLogin_WrongPassword() {
        // Given: Existing user
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("correctpassword"));
        user.setFirstName("Test");
        user.setLastName("User");
        userRepository.save(user);

        // When: Login with wrong password
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@example.com", "wrongpassword");

        // Then: Should throw exception
        assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        }, "Should throw exception for wrong password");
    }

    @Test
    void testLogin_UserNotFound() {
        // Given: No user in database

        // When: Try to login
        LoginRequestDTO loginRequest = new LoginRequestDTO("nonexistent@example.com", "password");

        // Then: Should throw exception
        assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        }, "Should throw exception for non-existent user");
    }

    @Test
    void testRegister_PasswordIsEncrypted() {
        // Given: Registration request
        RegisterRequestDTO request = new RegisterRequestDTO("Test", "User", "test@example.com", "plainpassword");

        // When: Register
        authService.register(request);

        // Then: Password in database should be encrypted
        User user = userRepository.findByEmail("test@example.com").orElseThrow();
        assertNotEquals("plainpassword", user.getPassword(),
                "Password should not be stored as plain text");
        assertTrue(user.getPassword().startsWith("$2a$"),
                "Password should be BCrypt encrypted");
    }
}
