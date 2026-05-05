package JoaquimManjama.ChangelogGenerator.DTOs;

import java.time.LocalDateTime;

public record GitHubCommitAuthor(
        String name,
        LocalDateTime date
    ){
}
