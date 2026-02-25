package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ProjectRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("projects")
public class ProjectControler {
    @Autowired
    private ProjectService service;

    // Create a project
    @PostMapping("new")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDTO projectRequestDTO, @AuthenticationPrincipal User user) {
        Project project = service.addProject(user, projectRequestDTO);
        ProjectDTO response = new ProjectDTO(project.getId(), project.getName(), project.getSlug(), project.getUser().getId(), project.getGithubRepo());
        return ResponseEntity.ok(response);
    }

    // Get all users projects
    @GetMapping("/all")
    public ResponseEntity<?> getProjects(@AuthenticationPrincipal User user) {
        List<ProjectDTO> response = service.getProjects(user);
        return ResponseEntity.ok(response);
    }

    // Get a single project
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProject(@AuthenticationPrincipal User user, @PathVariable Long id) {
        ProjectDTO project = service.getProject(user, id);
        return ResponseEntity.ok(project);
    }

    // Update a project
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@AuthenticationPrincipal User user, @RequestBody ProjectRequestDTO projectRequest, @PathVariable Long id) {
        ProjectDTO project = service.updateProject(projectRequest, user.getId(), id);
        return ResponseEntity.ok(project);
    }

    // Delete a Project
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@AuthenticationPrincipal User user, @PathVariable Long id) {
        ProjectDTO project = service.deleteProject(user.getId(), id);
        return ResponseEntity.ok(project);
    }
}