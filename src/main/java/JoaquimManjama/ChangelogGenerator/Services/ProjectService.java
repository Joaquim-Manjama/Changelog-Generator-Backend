package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ProjectRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    public Project addProject(User user, ProjectRequestDTO projectRequestDTO) {
        Project project = new Project();
        project.setName(projectRequestDTO.name());
        project.setSlug(projectRequestDTO.slug());
        project.setGithubRepo(projectRequestDTO.githubRepo());
        project.setUser(user);

        repository.save(project);
        return project;
    }

    public List<ProjectDTO> getProjects(User user) {
        List<Project> userProjects = repository.findByUserId(user.getId());

        List<ProjectDTO> projects = userProjects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return projects;
    }

    public ProjectDTO getProject(User user, Long id) {
        Project project = repository.findByUserIdAndId(user.getId(), id);
        return convertToDTO(project);
    }

    public ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName(), project.getSlug(), project.getUser().getId(), project.getGithubRepo());
    }

    public ProjectDTO updateProject(ProjectRequestDTO projectRequest, Long userId, Long id) {
        Project project = repository.findByUserIdAndId(userId, id);

        if (project != null) {

            project.setName(projectRequest.name());
            project.setSlug(projectRequest.slug());
            project.setGithubRepo(projectRequest.githubRepo());

            repository.save(project);
            return  convertToDTO(project);
        }

        return null;
    }

    public ProjectDTO deleteProject(Long userId, Long id) {
        Project project = repository.findByUserIdAndId(userId, id);

        if  (project != null) {
            repository.deleteById(id);
            return convertToDTO(project);
        }

        return null;
    }
}
