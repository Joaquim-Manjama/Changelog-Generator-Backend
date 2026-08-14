package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {

    Optional<Subscriber> findByEmail(String email);
    Optional<Subscriber> findByEmailAndProject(String email, Project project);
}
