package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.Entities.Project;
import JoaquimManjama.ChangelogGenerator.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("projects")
public class ProjectControler {
    @Autowired
    private ProjectService service;

    @GetMapping("/all{id}")
    public List<ProjectDTO> getAllProjects(@RequestParam long id) {
        return service.getUserProjects(id);
    }

    @PostMapping("/new{id}")
    public ProjectDTO addProject(@RequestParam long id, @RequestBody Project project) {
        ProjectDTO saved = service.addProject(id, project);
        return saved;
    }
}
