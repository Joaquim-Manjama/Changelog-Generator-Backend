package JoaquimManjama.ChangelogGenerator.DTOs;

import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    List<Project> projects;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.projects = user.getProjects();
    }

}
