package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRequestDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String name = "My Project";
        String slug = "my-project";
        String githubRepo = "my-org/my-repo";

        // When
        ProjectRequestDTO dto = new ProjectRequestDTO(name, slug, githubRepo);

        // Then
        assertEquals(name, dto.name());
        assertEquals(slug, dto.slug());
        assertEquals(githubRepo, dto.githubRepo());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        ProjectRequestDTO dto1 = new ProjectRequestDTO("Project", "proj", "org/repo");
        ProjectRequestDTO dto2 = new ProjectRequestDTO("Project", "proj", "org/repo");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
