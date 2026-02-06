package JoaquimManjama.ChangelogGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ChangelogGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChangelogGeneratorApplication.class, args);
	}

    @GetMapping
    public static String helloWorld() {
        return "Hello World!";
    }
}
