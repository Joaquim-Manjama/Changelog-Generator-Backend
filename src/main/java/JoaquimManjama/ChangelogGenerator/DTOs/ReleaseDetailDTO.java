package JoaquimManjama.ChangelogGenerator.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public record ReleaseDetailDTO(String version, String description, LocalDateTime createdAt, String status, LocalDateTime released, List<ChangelogEntryInfoDTO> features, List<ChangelogEntryInfoDTO> fixes, List<ChangelogEntryInfoDTO> improvements) {
}
