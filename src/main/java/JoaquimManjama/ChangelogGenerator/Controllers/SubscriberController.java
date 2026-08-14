package JoaquimManjama.ChangelogGenerator.Controllers;

import JoaquimManjama.ChangelogGenerator.DTOs.SubscriberRequestDTO;
import JoaquimManjama.ChangelogGenerator.Services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscription")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody SubscriberRequestDTO subscriber) {
        return subscriberService.addSubscriber(subscriber);
    }

    @DeleteMapping
    public ResponseEntity<?> unsubscribe(@RequestBody SubscriberRequestDTO subscriber) {
        return subscriberService.deleteSubscriber(subscriber.email(), subscriber.slug());
    }

}
