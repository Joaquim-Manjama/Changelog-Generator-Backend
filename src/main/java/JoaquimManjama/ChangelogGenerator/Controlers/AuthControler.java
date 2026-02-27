package JoaquimManjama.ChangelogGenerator.Controlers;

import java.util.Map;
import java.util.HashMap;

import JoaquimManjama.ChangelogGenerator.DTOs.AuthResponseDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.LoginRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.RegisterRequestDTO;
import JoaquimManjama.ChangelogGenerator.DTOs.UserDTO;
import JoaquimManjama.ChangelogGenerator.Models.User;
import JoaquimManjama.ChangelogGenerator.Services.AuthService;
import JoaquimManjama.ChangelogGenerator.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControler {

    @Autowired
    AuthService service;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        String token = service.register(request);
        AuthResponseDTO response = new AuthResponseDTO(request.firstName(), request.lastName(), request.email(), token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String token = service.login(request);
        UserDTO user = userService.getUserByEmail(request.email());
        AuthResponseDTO response = new AuthResponseDTO(user.firstName(), user.lastName(), request.email(), token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        return ResponseEntity.ok(response);
    }
}