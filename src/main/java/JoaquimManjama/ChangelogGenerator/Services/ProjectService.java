package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    public ProjectDTO addProject(Long user_id, Project project) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            project.setUser(user.get());
            projectRepository.save(project);
        }

        return new ProjectDTO(project);
    }

    public List<ProjectDTO> getUserProjects(long user_id) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isPresent()) {
            List<Project> projects = user.get().getProjects();
            return projects.stream()
                    .map(ProjectDTO::new)
                    .collect(Collectors.toList());
        }

        return null;
    }
}
