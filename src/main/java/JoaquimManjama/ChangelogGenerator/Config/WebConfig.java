package JoaquimManjama.ChangelogGenerator.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ClientConfig clientConfig;

    public WebConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigin = clientConfig.getClientUrl();

        registry.addMapping("/public/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/subscription")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("POST", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/auth/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/projects/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/github/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}