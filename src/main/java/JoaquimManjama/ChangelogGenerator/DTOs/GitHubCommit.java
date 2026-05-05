package JoaquimManjama.ChangelogGenerator.DTOs;

public record GitHubCommit(
        String message,
        GitHubCommitAuthor author
    ){
}
