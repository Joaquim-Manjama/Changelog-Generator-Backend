package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.*;
import JoaquimManjama.ChangelogGenerator.Enums.ReleaseStatus;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.Release;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InformationService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ReleaseRepository releaseRepository;

    public ProjectInfoDTO getProjectBySlug(String slug) {

        Optional<Project> possibleProject = projectRepository.findBySlug(slug);

        if (possibleProject.isPresent()) {
            Project project = possibleProject.get();
            return  convertToDTO(project);
        }
        return null;
    }

    public ReleaseDetailDTO getReleaseByVersionAndProjectSlug(String version, String projectSlug) {
        Optional<Release> possibleRelease = releaseRepository.findByVersionAndProjectSlug(version, projectSlug);

        if (possibleRelease.isPresent()) {
            Release release = possibleRelease.get();

            return convertToDetailDTO(release);
        }

        return null;
    }

    public ProjectInfoDTO convertToDTO(Project project) {

        List<Release> releases = project.getReleases();
        List<ReleaseInfoDTO> publishedReleasesInfo = new ArrayList<>();

        for (Release release : releases) {
            if (release.getStatus().equals(ReleaseStatus.PUBLISHED))
                publishedReleasesInfo.add(convertToDTO(release));
        }
        return new ProjectInfoDTO(project.getName(), project.getSlug(), project.getGithubRepo(), publishedReleasesInfo);
    }

    public ReleaseInfoDTO convertToDTO(Release release) {
        return new ReleaseInfoDTO(release.getVersion(), release.getDescription());
    }

    public ReleaseDetailDTO convertToDetailDTO(Release release) {
        return new ReleaseDetailDTO(
                release.getVersion(),
                release.getDescription(),
                release.getCreatedAt(),
                release.getStatus().toString(),
                release.getReleaseDate(),
                release.getNumberOfFeatures(),
                release.getNumberOfFixes(),
                release.getNumberOfImprovements()
        );
    }


}
