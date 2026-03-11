package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Release;
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
    public ResponseEntity<?> release(@AuthenticationPrincipal User user, @RequestBody ReleaseRequestDTO releaseRequestDTO) {
        ReleaseDTO release = service.createRelease(releaseRequestDTO, user);
        return ResponseEntity.ok().body(release);
    }

    @GetMapping("/{id}/releases/all")
    public ResponseEntity<?> getAllReleases(@AuthenticationPrincipal User user, @PathVariable Long id) {
        List<ReleaseDTO> releases = service.getReleases(user, id);
        return ResponseEntity.ok().body(releases);
    }
}
