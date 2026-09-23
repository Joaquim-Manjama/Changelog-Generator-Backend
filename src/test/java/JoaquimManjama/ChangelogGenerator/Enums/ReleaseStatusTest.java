package JoaquimManjama.ChangelogGenerator.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReleaseStatusTest {

    @Test
    void testEnumValues() {
        // Then
        ReleaseStatus[] values = ReleaseStatus.values();
        assertEquals(2, values.length);
        assertArrayEquals(new ReleaseStatus[]{ReleaseStatus.DRAFT, ReleaseStatus.PUBLISHED}, values);
    }

    @Test
    void testDraftValue() {
        // Then
        assertEquals("DRAFT", ReleaseStatus.DRAFT.name());
    }

    @Test
    void testPublishedValue() {
        // Then
        assertEquals("PUBLISHED", ReleaseStatus.PUBLISHED.name());
    }
}
