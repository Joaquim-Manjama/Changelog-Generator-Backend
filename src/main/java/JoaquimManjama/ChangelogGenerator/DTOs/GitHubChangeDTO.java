package JoaquimManjama.ChangelogGenerator.DTOs;

import JoaquimManjama.ChangelogGenerator.Enums.GitHubChangeType;

import java.time.LocalDateTime;
import java.util.List;

public record GitHubChangeDTO(
        GitHubChangeType type,
        String id,
        String title,
        String description,
        String author,
        LocalDateTime date,
        List<String> labels,
        String url
) {
}
