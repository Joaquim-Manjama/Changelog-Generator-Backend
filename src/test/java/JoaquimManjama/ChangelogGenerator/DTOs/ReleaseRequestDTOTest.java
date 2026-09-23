package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReleaseRequestDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String projectId = "project-123";
        String version = "1.0.0";
        String description = "Initial release";

        // When
        ReleaseRequestDTO dto = new ReleaseRequestDTO(projectId, version, description);

        // Then
        assertEquals(projectId, dto.projectId());
        assertEquals(version, dto.version());
        assertEquals(description, dto.description());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        ReleaseRequestDTO dto1 = new ReleaseRequestDTO("proj1", "1.0", "desc");
        ReleaseRequestDTO dto2 = new ReleaseRequestDTO("proj1", "1.0", "desc");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
