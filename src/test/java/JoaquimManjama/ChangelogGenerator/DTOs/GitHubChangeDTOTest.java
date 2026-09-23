package JoaquimManjama.ChangelogGenerator.DTOs;

import JoaquimManjama.ChangelogGenerator.Enums.GitHubChangeType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubChangeDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        GitHubChangeType type = GitHubChangeType.COMMIT;
        String id = "commit-sha-123";
        String title = "Fix bug in login";
        String description = "Fixed a null pointer";
        String author = "developer";
        LocalDateTime date = LocalDateTime.now();
        List<String> labels = List.of("bug", "fix");
        String url = "https://github.com/org/repo/commit/123";

        // When
        GitHubChangeDTO change = new GitHubChangeDTO(type, id, title, description, author, date, labels, url);

        // Then
        assertEquals(type, change.type());
        assertEquals(id, change.id());
        assertEquals(title, change.title());
        assertEquals(description, change.description());
        assertEquals(author, change.author());
        assertEquals(date, change.date());
        assertEquals(labels, change.labels());
        assertEquals(url, change.url());
    }
}
