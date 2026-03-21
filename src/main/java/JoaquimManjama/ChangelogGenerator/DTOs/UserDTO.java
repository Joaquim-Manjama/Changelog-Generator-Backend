package JoaquimManjama.ChangelogGenerator.DTOs;

import JoaquimManjama.ChangelogGenerator.Models.Project;

import java.util.List;

public record UserDTO(String id, String firstName, String lastName, String email, List<Project> projects) {
}
