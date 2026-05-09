package JoaquimManjama.ChangelogGenerator.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record GitHubPullRequestDTO(
        Long id,
        int number,
        String title,
        String body,
        String state,
        @JsonProperty("merged_at") LocalDateTime mergedAt,
        List<GitHubLabel> labels,
        @JsonProperty("html_url") String html_url

) {
}
