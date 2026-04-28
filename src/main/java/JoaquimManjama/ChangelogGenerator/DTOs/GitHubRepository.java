package JoaquimManjama.ChangelogGenerator.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepository(
        Long id,
        String name,
        @JsonProperty("full_name") String fullName,
        String description,
        @JsonProperty("html_url") String htmlUrl,
        @JsonProperty("default_branch") String defaultBranch) {
}
