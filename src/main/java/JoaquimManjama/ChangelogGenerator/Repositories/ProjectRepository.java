package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
