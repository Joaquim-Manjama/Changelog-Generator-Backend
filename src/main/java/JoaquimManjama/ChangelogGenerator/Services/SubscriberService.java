package JoaquimManjama.ChangelogGenerator.Services;

import JoaquimManjama.ChangelogGenerator.DTOs.SubscriberRequestDTO;
import JoaquimManjama.ChangelogGenerator.Models.Project;
import JoaquimManjama.ChangelogGenerator.Models.Subscriber;
import JoaquimManjama.ChangelogGenerator.Repositories.ProjectRepository;
import JoaquimManjama.ChangelogGenerator.Repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ResponseEntity addSubscriber(SubscriberRequestDTO subscriberRequestDTO) {

        Optional<Subscriber> possibleSubscriber = subscriberRepository.findByEmail(subscriberRequestDTO.email());

        if (possibleSubscriber.isPresent()) {
            return new ResponseEntity("Email already Exists", HttpStatus.ALREADY_REPORTED);
        }

        Optional<Project> project =  projectRepository.findBySlug(subscriberRequestDTO.slug());

        if  (project.isPresent()) {
            Subscriber newSubscriber = new Subscriber();
            newSubscriber.setEmail(subscriberRequestDTO.email());
            newSubscriber.setProject(project.get());
            subscriberRepository.save(newSubscriber);
            return new ResponseEntity("Subscriber Created!", HttpStatus.CREATED);
        }

        return new ResponseEntity("Project Not Found!", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity deleteSubscriber(String email, String slug) {
        Optional<Project> project = projectRepository.findBySlug(slug);

        if (project.isPresent()) {
            Optional<Subscriber> possibleSubscriber = subscriberRepository.findByEmailAndProject(email , project.get());

            if (possibleSubscriber.isPresent()) {
                subscriberRepository.delete(possibleSubscriber.get());
                return new ResponseEntity("Subscriber Deleted!", HttpStatus.OK);
            }

            return new ResponseEntity("Subscriber Not Found!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity("Project Not Found!", HttpStatus.NOT_FOUND);
    }
}
