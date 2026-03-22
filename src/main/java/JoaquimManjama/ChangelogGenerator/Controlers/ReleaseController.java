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
    public ResponseEntity<?> createRelease(@AuthenticationPrincipal User user, @RequestBody ReleaseRequestDTO releaseRequestDTO) {
        ReleaseDTO release = service.createRelease(releaseRequestDTO);
        return ResponseEntity.ok().body(release);
    }

    @GetMapping("/{projectId}/releases/all")
    public ResponseEntity<?> getAllReleases(@AuthenticationPrincipal User user, @PathVariable String projectId) {
        List<ReleaseDTO> releases = service.getReleases(projectId);
        return ResponseEntity.ok().body(releases);
    }

    @PutMapping("/releases/update")
    public ResponseEntity<?> updateRelease(@AuthenticationPrincipal User user,  @RequestBody ReleaseRequestDTO releaseRequestDTO) {
        ReleaseDTO release = service.updateRelease(releaseRequestDTO);
        return ResponseEntity.ok().body(release);
    }

    @DeleteMapping("/releases/delete/{id}")
    public ResponseEntity<?> deleteRelease(@AuthenticationPrincipal User user, @PathVariable String id) {
        ReleaseDTO release = service.deleteRelease(id);
        return ResponseEntity.ok().body(release);
    }

    @PatchMapping("/release/publish/{id}")
    public ResponseEntity<?> toggleReleaseStatus(@AuthenticationPrincipal User user, @PathVariable String id) {
        ReleaseDTO release = service.toggleReleaseStatus(id);
        return ResponseEntity.ok().body(release);
    }
}
