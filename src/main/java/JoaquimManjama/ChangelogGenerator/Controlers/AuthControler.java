package JoaquimManjama.ChangelogGenerator.Controlers;

import java.util.Map;
import java.util.HashMap;

import JoaquimManjama.ChangelogGenerator.DTOs.AuthResponseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.UserDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControler {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        String token = service.register(request);
        AuthResponseDTO response = new AuthResponseDTO(token, request.email());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String token = service.login(request);
        AuthResponseDTO response = new AuthResponseDTO(token, request.email());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
        // Create a response without password
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getFirstName());
        response.put("surname", user.getFirstName());
        return ResponseEntity.ok(response);
    }
}