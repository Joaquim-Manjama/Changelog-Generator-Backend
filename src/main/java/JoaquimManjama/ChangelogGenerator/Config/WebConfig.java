package JoaquimManjama.ChangelogGenerator.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/auth/**")
                .allowedOrigins("http://localhost:5173")  // Your React app URLs
                .allowedMethods("GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/projects/**")
                .allowedOrigins("http://localhost:5173")  // Your React app URLs
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
