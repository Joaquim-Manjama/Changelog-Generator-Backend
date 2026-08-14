package JoaquimManjama.ChangelogGenerator.Controllers;

import JoaquimManjama.ChangelogGenerator.DTOs.ProjectInfoDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseDetailDTO;
import JoaquimManjama.ChangelogGenerator.Services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private InformationService  informationService;

    @GetMapping("project/{projectSlug}")
    public ResponseEntity<?> getProject(@PathVariable String projectSlug) {

        ProjectInfoDTO project = informationService.getProjectBySlug(projectSlug);

        if  (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(project);
    }

    @GetMapping("project/{projectSlug}/release/{version}")
    public ResponseEntity<?> getRelease(@PathVariable String projectSlug, @PathVariable String version) {
        ReleaseDetailDTO release = informationService.getReleaseByVersionAndProjectSlug(version, projectSlug);

        if (release == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(release);
    }
}
