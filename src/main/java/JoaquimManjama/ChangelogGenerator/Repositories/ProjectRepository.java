package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    public List<Project> findByUserIdOrderByIdAsc(Long userId);
    public Project findByUserIdAndId(Long userId, Long id);
}
