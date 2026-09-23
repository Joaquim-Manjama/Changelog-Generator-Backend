package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String id = "project-123";
        String name = "My Project";
        String slug = "my-project";
        String githubRepo = "my-org/my-repo";

        // When
        ProjectDTO dto = new ProjectDTO(id, name, slug, githubRepo);

        // Then
        assertEquals(id, dto.id());
        assertEquals(name, dto.name());
        assertEquals(slug, dto.slug());
        assertEquals(githubRepo, dto.githubRepo());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        ProjectDTO dto1 = new ProjectDTO("1", "Project", "proj", "org/repo");
        ProjectDTO dto2 = new ProjectDTO("1", "Project", "proj", "org/repo");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
