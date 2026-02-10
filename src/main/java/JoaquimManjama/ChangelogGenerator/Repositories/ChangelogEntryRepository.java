package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.ChangelogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangelogEntryRepository extends JpaRepository<ChangelogEntry, Long> {
}
