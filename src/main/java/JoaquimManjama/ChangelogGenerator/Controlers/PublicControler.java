package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectDTO;
import JoaquimManjama.ChangelogGenerator.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicControler {

    @Autowired
    private ProjectService projectService;

    @GetMapping("project/{projectSlug}")
    public ResponseEntity<?> getProject(@PathVariable String projectSlug) {

        ProjectDTO projectDTO = projectService.getProjectBySlug(projectSlug);

        if  (projectDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(projectDTO);
    }
}
