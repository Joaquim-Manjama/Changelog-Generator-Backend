package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangelogEntryDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String id = "entry-123";
        String description = "Fixed a bug";
        Integer displayOrder = 1;
        String category = "BUG_FIX";

        // When
        ChangelogEntryDTO dto = new ChangelogEntryDTO(id, description, displayOrder, category);

        // Then
        assertEquals(id, dto.id());
        assertEquals(description, dto.description());
        assertEquals(displayOrder, dto.displayOrder());
        assertEquals(category, dto.category());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        ChangelogEntryDTO dto1 = new ChangelogEntryDTO("1", "desc", 1, "FEATURE");
        ChangelogEntryDTO dto2 = new ChangelogEntryDTO("1", "desc", 1, "FEATURE");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testRecordNotEqualsWhenDifferent() {
        // Given
        ChangelogEntryDTO dto1 = new ChangelogEntryDTO("1", "desc", 1, "FEATURE");
        ChangelogEntryDTO dto2 = new ChangelogEntryDTO("2", "desc", 1, "FEATURE");

        // Then
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testRecordToString() {
        // Given
        ChangelogEntryDTO dto = new ChangelogEntryDTO("1", "desc", 1, "FEATURE");

        // Then
        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("1"));
        assertTrue(str.contains("desc"));
    }
}
