package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.Entities.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User addUser(User user) {
        repository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(long id) {
        return repository.findById(id).get();
    }
}
