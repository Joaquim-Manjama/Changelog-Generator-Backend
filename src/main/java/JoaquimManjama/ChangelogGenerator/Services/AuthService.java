package JoaquimManjama.ChangelogGenerator.Services;

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

    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder;

    public String register(User possibleUser) {
        Optional<User> user = repository.findByEmail(possibleUser.getEmail());

        if(user.isEmpty()){
            String encryptedPassword = passwordEncoder.encode(possibleUser.getPassword());
            User newUser = new User(possibleUser.getEmail(), encryptedPassword, possibleUser.getFirstName(), possibleUser.getLastName());
            repository.save(newUser);
            return jwtUtil.generateToken(possibleUser.getEmail());
        }

        return null;
    }

    public String login(String email, String password) {

        Optional<User> possibleUser = repository.findByEmail(email);

        if (possibleUser.isPresent()){
            User user = possibleUser.get();
            String userPassword = user.getPassword();

            if (passwordEncoder.matches(password,userPassword)){
                return jwtUtil.generateToken(email);
            }

            return null;
        }
        return null;
    }
}
