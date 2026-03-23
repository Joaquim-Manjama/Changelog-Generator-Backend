package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ChangelogEntryDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ChangelogEntryRequestDTO;
import JoaquimManjama.ChangelogGenerator.Enums.EntryCategory;
import JoaquimManjama.ChangelogGenerator.Models.ChangelogEntry;
import JoaquimManjama.ChangelogGenerator.Models.Release;
import JoaquimManjama.ChangelogGenerator.Repositories.ChangelogEntryRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ChangelogEntryService {

    @Autowired
    private ChangelogEntryRepository repository;

    @Autowired
    private ReleaseRepository releaseRepository;

    public ChangelogEntryDTO addEntry(ChangelogEntryRequestDTO changelogEntryRequestDTO, String releaseId) {
        Optional<Release> possibleRelease = releaseRepository.findById(releaseId);

        if (possibleRelease.isPresent()) {
            Release release = possibleRelease.get();

            ChangelogEntry newChangelogEntry = new ChangelogEntry();
            newChangelogEntry.setRelease(release);
            newChangelogEntry.setDescription(changelogEntryRequestDTO.description());
            newChangelogEntry.setDisplayOrder(changelogEntryRequestDTO.displayOrder());
            newChangelogEntry.setCategory(EntryCategory.fromString(changelogEntryRequestDTO.category()));
            repository.save(newChangelogEntry);

            return convertToDTO(newChangelogEntry);
        }

        return null;
    }

    public List<ChangelogEntryDTO> getEntries(String releaseId) {
        Optional<Release> possibleRelease = releaseRepository.findById(releaseId);

        if (possibleRelease.isPresent()) {
            Release release = possibleRelease.get();
            List<ChangelogEntry> changelogEntries = release.getChangelogEntries();

            return changelogEntries.stream().sorted(Comparator.comparing(ChangelogEntry::getDisplayOrder)).map(this::convertToDTO).toList();
        }

        return null;
    }

    public ChangelogEntryDTO updateEntry(ChangelogEntryRequestDTO changelogEntryRequestDTO, String id) {
        Optional<ChangelogEntry> possibleEntry = repository.findById(id);

        if (possibleEntry.isPresent()) {
            ChangelogEntry changelogEntry = possibleEntry.get();
            changelogEntry.setCategory(EntryCategory.fromString(changelogEntryRequestDTO.category()));
            changelogEntry.setDescription(changelogEntryRequestDTO.description());
            changelogEntry.setDisplayOrder(changelogEntryRequestDTO.displayOrder());

            repository.save(changelogEntry);
            return convertToDTO(changelogEntry);
        }

        return null;
    }

    @Transactional
    public ChangelogEntryDTO deleteEntry(String id) {
        Optional<ChangelogEntry> possibleEntry = repository.findById(id);

        if (possibleEntry.isPresent()) {
            ChangelogEntry changelogEntry = possibleEntry.get();
            repository.delete(changelogEntry);
            return convertToDTO(changelogEntry);
        }

        return null;
    }

    public ChangelogEntryDTO convertToDTO(ChangelogEntry changelogEntry) {
        return new ChangelogEntryDTO(changelogEntry.getId(),  changelogEntry.getDescription(), changelogEntry.getDisplayOrder());
    }


}
