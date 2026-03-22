package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseRequestDTO;
import JoaquimManjama.ChangelogGenerator.Enums.ReleaseStatus;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.Release;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReleaseService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReleaseRepository repository;

    public ReleaseDTO createRelease(ReleaseRequestDTO releaseRequestDTO) {
        Optional<Project> possibleProject = projectRepository.findById(releaseRequestDTO.projectId());

        if (possibleProject.isPresent()) {
            Project project = possibleProject.get();

            Release release = new Release();
            release.setVersion(releaseRequestDTO.version());
            release.setDescription(releaseRequestDTO.description());
            release.setProject(project);

            repository.save(release);
            return convertToDTO(release);
        }

        return null;
    }

    private ReleaseDTO convertToDTO(Release release) {
        return new ReleaseDTO(release.getId(), release.getVersion(), release.getDescription(), release.getCreatedAt(), release.getStatus().toString());
    }

    public List<ReleaseDTO> getReleases(String projectId) {
        Optional<Project> possibleProject = projectRepository.findById(projectId);

        if (possibleProject.isPresent()) {
            Project project = possibleProject.get();
            return project.getReleases().stream().sorted(Comparator.comparing(Release::getCreatedAt)).map(this::convertToDTO).toList();
        }

        return null;
    }

    public ReleaseDTO updateRelease(ReleaseRequestDTO releaseRequestDTO) {
        Optional<Release> release = repository.findById(releaseRequestDTO.projectId());

        if (release.isPresent()) {
            Release updatedRelease = release.get();
            updatedRelease.setVersion(releaseRequestDTO.version());
            updatedRelease.setDescription(releaseRequestDTO.description());

            repository.save(updatedRelease);
            return convertToDTO(updatedRelease);
        }

        return null;
    }

    @Transactional
    public ReleaseDTO deleteRelease(String releaseId) {
        Optional<Release> possibleRelease = repository.findById(releaseId);

        if (possibleRelease.isPresent()) {
            Release release = possibleRelease.get();
            repository.delete(release);
            return convertToDTO(release);
        }

        return null;
    }

    public ReleaseDTO toggleReleaseStatus(String id) {
        Optional<Release> possibleRelease = repository.findById(id);

        if (possibleRelease.isPresent()) {
            Release release = possibleRelease.get();

            if ("DRAFT".equals(release.getStatus().toString())) {
              release.setStatus(ReleaseStatus.PUBLISHED);
            } else {
                release.setStatus(ReleaseStatus.DRAFT);
            }

            repository.save(release);
            return convertToDTO(release);
        }

        return null;
    }
}
