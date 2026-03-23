package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ReleaseController {

    @Autowired
    private ReleaseService service;

    @PostMapping("/releases/new")
    public ResponseEntity<?> createRelease(@RequestBody ReleaseRequestDTO releaseRequestDTO) {
        ReleaseDTO release = service.createRelease(releaseRequestDTO);
        return ResponseEntity.ok().body(release);
    }

    @GetMapping("/{projectId}/releases/all")
    public ResponseEntity<?> getAllReleases(@PathVariable String projectId) {
        List<ReleaseDTO> releases = service.getReleases(projectId);
        return ResponseEntity.ok().body(releases);
    }

    @PutMapping("/releases/update")
    public ResponseEntity<?> updateRelease(@RequestBody ReleaseRequestDTO releaseRequestDTO) {
        ReleaseDTO release = service.updateRelease(releaseRequestDTO);
        return ResponseEntity.ok().body(release);
    }

    @DeleteMapping("/releases/delete/{id}")
    public ResponseEntity<?> deleteRelease(@PathVariable String id) {
        ReleaseDTO release = service.deleteRelease(id);
        return ResponseEntity.ok().body(release);
    }

    @PatchMapping("/release/publish/{id}")
    public ResponseEntity<?> toggleReleaseStatus(@PathVariable String id) {
        ReleaseDTO release = service.toggleReleaseStatus(id);
        return ResponseEntity.ok().body(release);
    }
}
