package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.ReleaseRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.Release;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReleaseService {

    @Autowired
    private ProjectRepository projectRepository;
    private ReleaseRepository repository;


    public ReleaseDTO createRelease(ReleaseRequestDTO releaseRequestDTO, User user) {
        Project project = projectRepository.findByUserIdAndId(user.getId(), releaseRequestDTO.projectId());
        Release release = new Release();
        release.setVersion(releaseRequestDTO.version());
        release.setDescription(releaseRequestDTO.description());
        release.setProject(project);

        repository.save(release);
        return convertToDTO(release);
    }

    private ReleaseDTO convertToDTO(Release release) {
        return  new ReleaseDTO(release.getId(), release.getVersion(), release.getDescription());
    }
}
