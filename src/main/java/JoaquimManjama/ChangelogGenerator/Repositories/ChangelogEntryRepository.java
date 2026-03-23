package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.ChangelogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangelogEntryRepository extends JpaRepository<ChangelogEntry, Long> {
    Optional<ChangelogEntry> findById(String id);
}
