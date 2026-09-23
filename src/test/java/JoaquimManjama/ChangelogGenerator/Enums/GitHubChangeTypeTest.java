package JoaquimManjama.ChangelogGenerator.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitHubChangeTypeTest {

    @Test
    void testEnumValues() {
        // Then
        GitHubChangeType[] values = GitHubChangeType.values();
        assertEquals(2, values.length);
        assertArrayEquals(new GitHubChangeType[]{GitHubChangeType.COMMIT, GitHubChangeType.PULL_REQUEST}, values);
    }

    @Test
    void testCommitValue() {
        // Then
        assertEquals("COMMIT", GitHubChangeType.COMMIT.name());
    }

    @Test
    void testPullRequestValue() {
        // Then
        assertEquals("PULL_REQUEST", GitHubChangeType.PULL_REQUEST.name());
    }
}
