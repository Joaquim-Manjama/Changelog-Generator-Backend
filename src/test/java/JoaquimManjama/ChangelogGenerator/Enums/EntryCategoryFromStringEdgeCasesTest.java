package JoaquimManjama.ChangelogGenerator.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryCategoryFromStringEdgeCasesTest {

    @Test
    void testFromString_whitespaceOnly() {
        // Then
        assertNull(EntryCategory.fromString("   "));
    }

    @Test
    void testFromString_whitespaceTrimmed() {
        // When/Then - the method uses exact match, so whitespace is not trimmed
        assertNull(EntryCategory.fromString(" BUG_FIX "));
    }

    @Test
    void testFromString_justBugFix() {
        // When/Then
        assertEquals(EntryCategory.BUG_FIX, EntryCategory.fromString("BUG_FIX"));
    }

    @Test
    void testFromString_mixedCase() {
        // When/Then
        assertEquals(EntryCategory.BUG_FIX, EntryCategory.fromString("bUg_FiX"));
        assertEquals(EntryCategory.IMPROVEMENT, EntryCategory.fromString("iMpRoVeMeNt"));
        assertEquals(EntryCategory.NEW_FEATURE, EntryCategory.fromString("nEw_FeAtUrE"));
    }

    @Test
    void testFromString_numberString() {
        // Then
        assertNull(EntryCategory.fromString("123"));
    }
}
