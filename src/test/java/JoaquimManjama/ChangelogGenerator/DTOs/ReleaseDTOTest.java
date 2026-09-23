package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReleaseDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String id = "release-123";
        String version = "1.0.0";
        String description = "Initial release";
        LocalDateTime createdAt = LocalDateTime.now();
        String status = "DRAFT";
        LocalDateTime released = LocalDateTime.now();
        Integer numberOfFeatures = 5;
        Integer numberOfFixes = 2;
        Integer numberOfImprovements = 3;

        // When
        ReleaseDTO dto = new ReleaseDTO(id, version, description, createdAt, status, released, numberOfFeatures, numberOfFixes, numberOfImprovements);

        // Then
        assertEquals(id, dto.id());
        assertEquals(version, dto.version());
        assertEquals(description, dto.description());
        assertEquals(createdAt, dto.createdAt());
        assertEquals(status, dto.status());
        assertEquals(released, dto.released());
        assertEquals(numberOfFeatures, dto.numberOfFeatures());
        assertEquals(numberOfFixes, dto.numberOfFixes());
        assertEquals(numberOfImprovements, dto.numberOfImprovements());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ReleaseDTO dto1 = new ReleaseDTO("1", "1.0", "desc", now, "DRAFT", now, 1, 2, 3);
        ReleaseDTO dto2 = new ReleaseDTO("1", "1.0", "desc", now, "DRAFT", now, 1, 2, 3);

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
