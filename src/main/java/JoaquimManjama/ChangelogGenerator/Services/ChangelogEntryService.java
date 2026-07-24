package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ChangelogEntryDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ChangelogEntryRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.GitHubChangeDTO;
import JoaquimManjama.ChangelogGenerator.Enums.EntryCategory;
import JoaquimManjama.ChangelogGenerator.Models.ChangelogEntry;
import JoaquimManjama.ChangelogGenerator.Models.Release;
import JoaquimManjama.ChangelogGenerator.Repositories.ChangelogEntryRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            ChangelogEntryDTO entry = add(release, changelogEntryRequestDTO);
            countEntries(release);
            return entry;
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
            Release release = changelogEntry.getRelease();
            countEntries(release);
            return convertToDTO(changelogEntry);
        }
        return null;
    }

    @Transactional
    public ChangelogEntryDTO deleteEntry(String id) {
        Optional<ChangelogEntry> possibleEntry = repository.findById(id);

        if (possibleEntry.isPresent()) {
            ChangelogEntry changelogEntry = possibleEntry.get();
            Release release = changelogEntry.getRelease();
            repository.delete(changelogEntry);
            countEntries(release);
            return convertToDTO(changelogEntry);
        }
        return null;
    }

    public List<ChangelogEntryDTO> importEntries(List<GitHubChangeDTO> changes, String releaseId) {
        List<ChangelogEntryDTO> importedEntries = new ArrayList<>();
        Optional<Release> possibleRelease = releaseRepository.findById(releaseId);

        if (possibleRelease.isPresent()) {
            Release release = possibleRelease.get();
            List<ChangelogEntryRequestDTO> entries = new ArrayList<>();

            for  (GitHubChangeDTO change : changes) {
                ChangelogEntryRequestDTO entry = convertChangeToEntry(change);
                importedEntries.add(add(release, entry));
            }
            countEntries(release);
        }
        return importedEntries;
    }

    private ChangelogEntryDTO convertToDTO(ChangelogEntry changelogEntry) {
        return new ChangelogEntryDTO(changelogEntry.getId(),  changelogEntry.getDescription(), changelogEntry.getDisplayOrder(), changelogEntry.getCategory().toString());
    }

    private ChangelogEntryRequestDTO convertChangeToEntry(GitHubChangeDTO change) {
        String category = determineCategory(change);
        ChangelogEntryRequestDTO entry = new ChangelogEntryRequestDTO(category, cleanMessage(change.title(), change.description()), 1000);
        return entry;
    }

    private String determineCategory(GitHubChangeDTO change) {
        if (change.labels() != null && !change.labels().isEmpty()) {
            for (String label : change.labels()) {
                if (label.toLowerCase().contains("feat")) return "NEW_FEATURE";
                if (label.toLowerCase().contains("fix")) return "BUG_FIX";
            }
        }

        String title = change.title() == null ? "" : change.title().toLowerCase();
        String description = change.description() == null ? "" :change.description().toLowerCase();
        if (description.contains("feat") || title.contains("feat")) return "NEW_FEATURE";
        if (description.contains("fix") || title.contains("fix")) return "BUG_FIX";

        return "IMPROVEMENT";
    }

    private String cleanMessage(String title, String description) {
        return title.isEmpty() ? clean(description.toLowerCase()): clean(title.toLowerCase());
    }

    private String clean(String message) {
        return message.replaceAll("^(feature|feat|fixed|fix|perf|docs|style|refactor|chore|changed|change|implemented|implement|added|add|created|create|worked|work|improved|improve):?/?\\s*", "");
    }

    private void countEntries(Release release) {
        int[] counts = {0, 0, 0};

        for (ChangelogEntry entry: release.getChangelogEntries()) {
            switch (entry.getCategory()) {
                case NEW_FEATURE ->  counts[0]++;
                case BUG_FIX -> counts[1]++;
                case IMPROVEMENT -> counts[2]++;
            }
        }

        release.setNumberOfFeatures(counts[0]);
        release.setNumberOfFixes(counts[1]);
        release.setNumberOfImprovements(counts[2]);
        releaseRepository.save(release);
    }

    private ChangelogEntryDTO add(Release release, ChangelogEntryRequestDTO changelogEntryRequestDTO) {
        ChangelogEntry newChangelogEntry = new ChangelogEntry();
        newChangelogEntry.setRelease(release);
        newChangelogEntry.setDescription(changelogEntryRequestDTO.description());
        newChangelogEntry.setDisplayOrder(changelogEntryRequestDTO.displayOrder());
        newChangelogEntry.setCategory(EntryCategory.fromString(changelogEntryRequestDTO.category()));
        repository.save(newChangelogEntry);
        return convertToDTO(newChangelogEntry);
    }
}
