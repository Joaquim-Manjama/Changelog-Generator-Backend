package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import JoaquimManjama.ChangelogGenerator.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequestDTO request) {
        // Try to get user
        Optional<User> possibleUser = repository.findByEmail(request.email());

        // Check if user does not exist
        if(possibleUser.isEmpty()){
            // If user does not exist
            String encryptedPassword = passwordEncoder.encode(request.password());
            User newUser = new User(request.email(), encryptedPassword, request.firstName(), request.lastName());
            repository.save(newUser);
            return jwtUtil.generateToken(request.email());
        }

        throw new RuntimeException("User already exists!");
    }

    public String login(LoginRequestDTO request) {
        // Try to get user
        Optional<User> possibleUser = repository.findByEmail(request.email());

        // Check if user exists
        if (possibleUser.isPresent()){
            // User found
            User user = possibleUser.get();
            String userPassword = user.getPassword();

            // Check if passwords match
            if (passwordEncoder.matches(request.password(),userPassword)){
                return jwtUtil.generateToken(request.email());
            }

            throw new RuntimeException("Password is incorrect!");
        }

        throw new RuntimeException("User not found!");
    }
}
