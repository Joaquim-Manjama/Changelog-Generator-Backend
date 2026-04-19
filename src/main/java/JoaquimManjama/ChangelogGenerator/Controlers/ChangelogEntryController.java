package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.ChangelogEntryDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ChangelogEntryRequestDTO;
import JoaquimManjama.ChangelogGenerator.Services.ChangelogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ChangelogEntryController {

    @Autowired
    ChangelogEntryService service;

    @GetMapping("releases/{releaseId}/entries")
    public ResponseEntity<?> getEntries(@PathVariable String releaseId) {
        List<ChangelogEntryDTO> entries = service.getEntries(releaseId);
        return ResponseEntity.ok().body(entries);
    }

    @PostMapping("releases/{releaseId}/entries/new")
    public ResponseEntity<?> addEntry(@RequestBody ChangelogEntryRequestDTO changelogEntryRequestDTO, @PathVariable String releaseId) {
        ChangelogEntryDTO changelogEntryDTO = service.addEntry(changelogEntryRequestDTO, releaseId);
        return ResponseEntity.ok().body(changelogEntryDTO);
    }

    @PutMapping("releases/entries/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody ChangelogEntryRequestDTO changelogEntryRequestDTO, @PathVariable String id) {
        ChangelogEntryDTO changelogEntryDTO = service.updateEntry(changelogEntryRequestDTO, id);
        return ResponseEntity.ok().body(changelogEntryDTO);
    }

    @DeleteMapping("releases/entries/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String id) {
        ChangelogEntryDTO changelogEntryDTO = service.deleteEntry(id);
        return ResponseEntity.ok().body(changelogEntryDTO);
    }
}
