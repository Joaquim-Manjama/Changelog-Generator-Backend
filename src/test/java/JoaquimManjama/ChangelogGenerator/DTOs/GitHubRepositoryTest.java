package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitHubRepositoryTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        Long id = 1L;
        String name = "my-repo";
        String fullName = "my-org/my-repo";
        String description = "A great project";
        String htmlUrl = "https://github.com/my-org/my-repo";
        String defaultBranch = "main";

        // When
        GitHubRepository repo = new GitHubRepository(id, name, fullName, description, htmlUrl, defaultBranch);

        // Then
        assertEquals(id, repo.id());
        assertEquals(name, repo.name());
        assertEquals(fullName, repo.fullName());
        assertEquals(description, repo.description());
        assertEquals(htmlUrl, repo.htmlUrl());
        assertEquals(defaultBranch, repo.defaultBranch());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        GitHubRepository repo1 = new GitHubRepository(1L, "repo", "org/repo", "desc", "https://url", "main");
        GitHubRepository repo2 = new GitHubRepository(1L, "repo", "org/repo", "desc", "https://url", "main");

        // Then
        assertEquals(repo1, repo2);
        assertEquals(repo1.hashCode(), repo2.hashCode());
    }
}
