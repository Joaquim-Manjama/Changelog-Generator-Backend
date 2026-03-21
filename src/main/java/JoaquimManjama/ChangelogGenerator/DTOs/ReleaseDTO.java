package JoaquimManjama.ChangelogGenerator.DTOs;

import java.time.LocalDateTime;

public record ReleaseDTO(String id, String version, String description, LocalDateTime createdAt) {
}
