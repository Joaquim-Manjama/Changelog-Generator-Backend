package JoaquimManjama.ChangelogGenerator.DTOs;

import java.time.LocalDateTime;
import java.time.LocalDate;

public record ReleaseDTO(String id, String version, String description, LocalDateTime createdAt, String status, LocalDateTime released) {
}
