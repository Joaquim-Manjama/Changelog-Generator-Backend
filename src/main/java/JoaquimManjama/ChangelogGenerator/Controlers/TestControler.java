package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.Entities.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestControler {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public void saveUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
