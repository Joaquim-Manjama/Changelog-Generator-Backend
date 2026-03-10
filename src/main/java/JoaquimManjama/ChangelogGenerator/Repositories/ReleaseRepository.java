package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.Release;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<Release, Long>{

    public Optional<Release> findByIdAndProjectId(Long id,Long projectId);
}
