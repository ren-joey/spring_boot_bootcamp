package example.controller;

import example.dto.LoginRequestDto;
import example.dto.RegisterRequestDto;
import example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/validate")
    public Map<String, String> validate(@Valid @RequestBody String token) {
        Map<String, String> map = new HashMap<>();
        map.put(
            "getName()",
            SecurityContextHolder.getContext().getAuthentication().getName()
        );
        map.put(
            "getPrincipal()",
            SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        map.put(
            "getAuthorities()",
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString()
        );
        map.put(
            "getDetails()",
            SecurityContextHolder.getContext().getAuthentication().getDetails().toString()
        );

        return map;
    }
}
