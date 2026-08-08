package JoaquimManjama.ChangelogGenerator.DTOs;

import java.util.List;

public record ProjectInfoDTO(
        String name,
        String slug,
        String githubRepo,
        List<ReleaseInfoDTO> publishedReleases
){
}
