package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john@example.com";
        String token = "eyJhbGciOiJIUzI1NiJ9.test.token";

        // When
        AuthResponseDTO dto = new AuthResponseDTO(firstName, lastName, email, token);

        // Then
        assertEquals(firstName, dto.firstName());
        assertEquals(lastName, dto.lastName());
        assertEquals(email, dto.email());
        assertEquals(token, dto.token());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        AuthResponseDTO dto1 = new AuthResponseDTO("John", "Doe", "john@example.com", "token1");
        AuthResponseDTO dto2 = new AuthResponseDTO("John", "Doe", "john@example.com", "token1");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testRecordNotEqualsWhenDifferent() {
        // Given
        AuthResponseDTO dto1 = new AuthResponseDTO("John", "Doe", "john@example.com", "token1");
        AuthResponseDTO dto2 = new AuthResponseDTO("Jane", "Doe", "jane@example.com", "token2");

        // Then
        assertNotEquals(dto1, dto2);
    }
}
