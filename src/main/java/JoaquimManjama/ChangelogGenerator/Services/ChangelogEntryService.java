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
            increaseEntryNumber(release, changelogEntryRequestDTO.category());

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
            Release release = changelogEntry.getRelease();
            decreaseEntryNumber(release, changelogEntry.getCategory());
            repository.delete(changelogEntry);
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
                ;
                increaseEntryNumber(release, entry.category());
                ChangelogEntryDTO newChangelogEntry = addEntry(entry, releaseId);
                importedEntries.add(newChangelogEntry);
            }

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

    private void increaseEntryNumber(Release release, String category) {
        switch (category) {
            case "NEW_FEATURE":
                release.addNumberOfFeatures();
                System.out.println(release.getNumberOfFeatures());
                break;
            case "BUG_FIX":
                release.addNumberOfFixes();
                break;
            case "IMPROVEMENT":
                release.addNumberOfImprovements();
                break;
            default:
                break;
        }
        releaseRepository.save(release);
    }

    private void decreaseEntryNumber(Release release, EntryCategory category) {
        switch (category) {
            case EntryCategory.NEW_FEATURE:
                release.subtractNumberOfFeatures();
                break;
            case EntryCategory.BUG_FIX:
                release.subtractNumberOfFixes();
                break;
            case EntryCategory.IMPROVEMENT:
                release.subtractNumberOfImprovements();
                break;
            default:
                break;
        }
        releaseRepository.save(release);
    }


}
