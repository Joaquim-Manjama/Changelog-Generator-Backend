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
        Optional<User> possibleUser = repository.findByEmail(request.email());

        if(possibleUser.isEmpty()){
            String encryptedPassword = passwordEncoder.encode(request.password());
            User newUser = new User(request.email(), encryptedPassword, request.firstName(), request.lastName());
            repository.save(newUser);
            return jwtUtil.generateToken(request.email());
        }
        return null;
    }

    public String login(LoginRequestDTO request) {
        Optional<User> possibleUser = repository.findByEmail(request.email());

        if (possibleUser.isPresent()){
            User user = possibleUser.get();
            String userPassword = user.getPassword();

            if (passwordEncoder.matches(request.password(),userPassword)){
                return jwtUtil.generateToken(request.email());
            }
            return null;
        }
        return null;
    }
}
