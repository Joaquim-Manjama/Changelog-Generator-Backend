package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String email = "john@example.com";
        String password = "securePassword123";

        // When
        LoginRequestDTO dto = new LoginRequestDTO(email, password);

        // Then
        assertEquals(email, dto.email());
        assertEquals(password, dto.password());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        LoginRequestDTO dto1 = new LoginRequestDTO("john@example.com", "pass1");
        LoginRequestDTO dto2 = new LoginRequestDTO("john@example.com", "pass1");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
