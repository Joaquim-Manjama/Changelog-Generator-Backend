package JoaquimManjama.ChangelogGenerator.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${host}")
    private String host;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/public/**")
                .allowedOrigins("http://"+host+":5173")
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/auth/**")
                .allowedOrigins("http://"+host+":5173")  // Your React app URLs
                .allowedMethods("GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/projects/**")
                .allowedOrigins("http://"+host+":5173")  // Your React app URLs
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/github/**")
                .allowedOrigins("http://"+host+":5173")  // Your React app URLs
                .allowedMethods("GET", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
