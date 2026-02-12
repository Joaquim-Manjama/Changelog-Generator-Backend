package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.AuthResponseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthControler {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        String token = service.register(request);
        return token == null ? null : new AuthResponseDTO(token);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        String token = service.login(request);
        return token == null ? null : new AuthResponseDTO(token);
    }
}