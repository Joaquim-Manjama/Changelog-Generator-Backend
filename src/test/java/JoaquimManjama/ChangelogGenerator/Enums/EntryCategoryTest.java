package JoaquimManjama.ChangelogGenerator.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryCategoryTest {

    @Test
    void testEnumValues() {
        // Then
        EntryCategory[] values = EntryCategory.values();
        assertEquals(3, values.length);
        assertArrayEquals(new EntryCategory[]{EntryCategory.BUG_FIX, EntryCategory.IMPROVEMENT, EntryCategory.NEW_FEATURE}, values);
    }

    @Test
    void testFromString_validCategory() {
        // When/Then
        assertEquals(EntryCategory.BUG_FIX, EntryCategory.fromString("BUG_FIX"));
        assertEquals(EntryCategory.IMPROVEMENT, EntryCategory.fromString("IMPROVEMENT"));
        assertEquals(EntryCategory.NEW_FEATURE, EntryCategory.fromString("NEW_FEATURE"));
    }

    @Test
    void testFromString_caseInsensitive() {
        // When/Then
        assertEquals(EntryCategory.BUG_FIX, EntryCategory.fromString("bug_fix"));
        assertEquals(EntryCategory.BUG_FIX, EntryCategory.fromString("Bug_Fix"));
        assertEquals(EntryCategory.NEW_FEATURE, EntryCategory.fromString("new_feature"));
    }

    @Test
    void testFromString_unknownCategory() {
        // When/Then
        assertNull(EntryCategory.fromString("UNKNOWN"));
        assertNull(EntryCategory.fromString(""));
    }

    @Test
    void testFromString_null() {
        // When/Then
        assertNull(EntryCategory.fromString(null));
    }
}
