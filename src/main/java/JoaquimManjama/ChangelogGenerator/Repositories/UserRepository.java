package JoaquimManjama.ChangelogGenerator.Repositories;

import JoaquimManjama.ChangelogGenerator.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public UserDetails findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    public User getByEmail(@Param("email") String email);
}
