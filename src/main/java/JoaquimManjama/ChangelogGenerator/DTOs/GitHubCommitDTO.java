package JoaquimManjama.ChangelogGenerator.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubCommitDTO(
        String sha,
        GitHubCommit commit,
        @JsonProperty("html_url") String html_url
    ){
}
