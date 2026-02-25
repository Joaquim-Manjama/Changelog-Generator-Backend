package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.UserDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User addUser(User user) {
        repository.save(user);
        return user;
    }

    public List<UserDTO> getAllUsers() {

        try{
            List<User> users = repository.findAll();
            return users.stream().map(this::convertToDTO).collect(Collectors.toList());

        } catch(Exception e){
            return null;
        }
    }

    public UserDTO getUserById(long id) {
        try {
            Optional<User> user = repository.findById(id);
            return user.isPresent() ? convertToDTO(user.get()) : null;

        } catch (Exception e) {
            return null;
        }
    }

    public UserDTO getUserByEmail(String email) {
        try {
            User user = repository.findByEmail(email).get();
            return convertToDTO(user);

        } catch (Exception e) {
            return null;
        }
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getProjects());
    }
}
