package JoaquimManjama.ChangelogGenerator.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void testProjectConstructorAndGetters() {
        // Given
        String id = "project-123";
        String name = "My Project";
        String slug = "my-project";
        String githubRepo = "org/repo";
        User user = new User("test@example.com", "pass", "Test", "User");
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setSlug(slug);
        project.setGithubRepo(githubRepo);
        project.setUser(user);
        project.setCreatedAt(createdAt);

        // Then
        assertEquals(id, project.getId());
        assertEquals(name, project.getName());
        assertEquals(slug, project.getSlug());
        assertEquals(githubRepo, project.getGithubRepo());
        assertEquals(user, project.getUser());
        assertEquals(createdAt, project.getCreatedAt());
    }

    @Test
    void testPrePersistSetsCreatedAt() {
        // Given
        Project project = new Project();
        project.setName("Test");
        project.setSlug("test");
        project.setUser(new User("a@b.com", "p", "A", "B"));

        // When
        project.onCreate();
        LocalDateTime createdAt = project.getCreatedAt();

        // Then
        assertNotNull(createdAt);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Project project = new Project();
        String id = "1";
        String name = "Name";
        String slug = "slug";
        String githubRepo = "org/repo";
        LocalDateTime now = LocalDateTime.now();

        // When
        project.setId(id);
        project.setName(name);
        project.setSlug(slug);
        project.setGithubRepo(githubRepo);
        project.setCreatedAt(now);

        // Then
        assertEquals(id, project.getId());
        assertEquals(name, project.getName());
        assertEquals(slug, project.getSlug());
        assertEquals(githubRepo, project.getGithubRepo());
        assertEquals(now, project.getCreatedAt());
    }
}
