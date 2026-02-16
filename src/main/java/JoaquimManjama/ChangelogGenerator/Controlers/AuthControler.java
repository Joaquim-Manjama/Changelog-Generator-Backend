package JoaquimManjama.ChangelogGenerator.Controlers;

import JoaquimManjama.ChangelogGenerator.DTOs.AuthResponseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControler {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        String token = service.register(request);
        return token == null ? null : new AuthResponseDTO(token, request.email());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        String token = service.login(request);
        //return token == null ? null : new AuthResponseDTO(token, request.email());
        return token == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(new AuthResponseDTO(token, request.email()));
    }
}