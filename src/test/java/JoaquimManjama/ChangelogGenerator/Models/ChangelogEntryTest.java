package JoaquimManjama.ChangelogGenerator.Models;

import JoaquimManjama.ChangelogGenerator.Enums.EntryCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChangelogEntryTest {

    @Test
    void testSettersAndGetters() {
        // Given
        ChangelogEntry entry = new ChangelogEntry();
        String id = "entry-1";
        String description = "Fixed a bug";
        Integer displayOrder = 1;
        EntryCategory category = EntryCategory.BUG_FIX;
        Release release = new Release();
        release.setId("release-1");
        release.setVersion("1.0");
        release.setProject(new Project());

        // When
        entry.setId(id);
        entry.setDescription(description);
        entry.setDisplayOrder(displayOrder);
        entry.setCategory(category);
        entry.setRelease(release);

        // Then
        assertEquals(id, entry.getId());
        assertEquals(description, entry.getDescription());
        assertEquals(displayOrder, entry.getDisplayOrder());
        assertEquals(category, entry.getCategory());
        assertEquals(release, entry.getRelease());
    }
}
