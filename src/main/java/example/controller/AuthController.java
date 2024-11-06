package example.controller;

import example.dto.LoginRequestDto;
import example.dto.RegisterRequestDto;
import example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }
}