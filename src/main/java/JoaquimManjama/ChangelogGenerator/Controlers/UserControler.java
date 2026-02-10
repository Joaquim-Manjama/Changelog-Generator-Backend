package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.UserDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
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
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/id{id}")
    public UserDTO getUserById(@RequestParam long id) {
        return service.getUserById(id);
    }

    @GetMapping("/email{email}")
    public UserDTO getUserByEmail(@RequestParam String email) {
        return service.getUserByEmail(email);
    }

    @PostMapping("/new")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }
}
