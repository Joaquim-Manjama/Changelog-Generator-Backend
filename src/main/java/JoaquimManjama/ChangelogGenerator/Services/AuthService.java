package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService{

    @Autowired
    private UserRepository repository;

    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).get();
    }
}
