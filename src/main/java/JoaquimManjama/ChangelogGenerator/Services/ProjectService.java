package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ProjectRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository repository;

    public ProjectDTO addProject(User user, ProjectRequestDTO projectRequestDTO) {
        Project project = new Project();
        project.setName(projectRequestDTO.name());
        project.setSlug(projectRequestDTO.slug());
        project.setGithubRepo(projectRequestDTO.githubRepo());
        project.setUser(user);

        repository.save(project);
        return convertToDTO(project);
    }

    public List<ProjectDTO> getProjects(User user) {
        List<Project> userProjects = repository.findByUserIdOrderByIdAsc(user.getId());

        List<ProjectDTO> projects = userProjects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return projects;
    }

    public ProjectDTO getProject(String id) {
        Optional<Project> possibleProject = repository.findById(id);

        if (possibleProject.isPresent()) {
            Project project = possibleProject.get();
            return convertToDTO(project);
        }

        return null;
    }

    public ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName(), project.getSlug(), project.getGithubRepo());
    }

    public ProjectDTO updateProject(ProjectRequestDTO projectRequest, String id) {
        Optional<Project> possibleProject = repository.findById(id);

        if (possibleProject.isPresent()) {
            Project project = possibleProject.get();
            project.setName(projectRequest.name());
            project.setSlug(projectRequest.slug());
            project.setGithubRepo(projectRequest.githubRepo());

            repository.save(project);
            return  convertToDTO(project);
        }

        return null;
    }

    @Transactional
    public ProjectDTO deleteProject(String id) {
        Optional<Project> possibleProject = repository.findById(id);

        if  (possibleProject.isPresent()) {
            Project project = possibleProject.get();
            repository.deleteById(id);
            return convertToDTO(project);
        }

        return null;
    }
}
