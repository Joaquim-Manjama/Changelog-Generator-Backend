package JoaquimManjama.ChangelogGenerator.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestDTOTest {

    @Test
    void testConstructorAndAccessors() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john@example.com";
        String password = "securePassword123";

        // When
        RegisterRequestDTO dto = new RegisterRequestDTO(firstName, lastName, email, password);

        // Then
        assertEquals(firstName, dto.firstName());
        assertEquals(lastName, dto.lastName());
        assertEquals(email, dto.email());
        assertEquals(password, dto.password());
    }

    @Test
    void testRecordEqualsAndHashCode() {
        // Given
        RegisterRequestDTO dto1 = new RegisterRequestDTO("John", "Doe", "john@example.com", "pass1");
        RegisterRequestDTO dto2 = new RegisterRequestDTO("John", "Doe", "john@example.com", "pass1");

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
