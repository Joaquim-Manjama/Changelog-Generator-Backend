package JoaquimManjama.ChangelogGenerator.DTOs;

import java.time.LocalDateTime;

public record ReleaseInfoDTO(String version, String description, LocalDateTime createdAt, String status, LocalDateTime released, Integer numberOfFeatures, Integer numberOfFixes, Integer numberOfImprovements) {
}
