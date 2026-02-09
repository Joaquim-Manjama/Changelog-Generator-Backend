package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.Entities.User;
import JoaquimManjama.ChangelogGenerator.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("users")
public class UserControler {

    @Autowired
    private UserService service;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/get{id}")
    public User getUsers(@RequestParam long id) {
        return service.getUserById(id);
    }

    @PostMapping("/new")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }
}
