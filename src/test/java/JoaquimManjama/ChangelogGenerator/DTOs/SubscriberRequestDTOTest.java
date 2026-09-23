package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberRequestDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String email = "subscriber@example.com";
        String slug = "my-project";

        // When
        SubscriberRequestDTO dto = new SubscriberRequestDTO(email, slug);

        // Then
        assertEquals(email, dto.email());
        assertEquals(slug, dto.slug());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        SubscriberRequestDTO dto1 = new SubscriberRequestDTO("sub@example.com", "proj");
        SubscriberRequestDTO dto2 = new SubscriberRequestDTO("sub@example.com", "proj");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
