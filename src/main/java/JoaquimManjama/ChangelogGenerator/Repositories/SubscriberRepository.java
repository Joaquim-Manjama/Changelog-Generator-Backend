package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
