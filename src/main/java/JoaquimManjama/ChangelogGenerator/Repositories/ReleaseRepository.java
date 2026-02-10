package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long>{
}
