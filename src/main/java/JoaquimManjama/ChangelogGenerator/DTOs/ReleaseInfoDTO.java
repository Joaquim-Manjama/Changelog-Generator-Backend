package JoaquimManjama.ChangelogGenerator.DTOs;

import java.time.LocalDateTime;

public record ReleaseInfoDTO(String version, String description, LocalDateTime releasedAt, Integer numberOfFeatures, Integer numberOfFixes, Integer numberOfImprovements) {
}
