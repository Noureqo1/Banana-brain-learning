package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.security.JwtService;
import BananaBrain.service.MyAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final MyAppUserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        MyAppUser user = new MyAppUser();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        userService.save(user);
        var userDetails = userService.loadUserByUsername(user.getUsername());
        var jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var userDetails = userService.loadUserByUsername(request.username());
        var jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }
}

record AuthenticationRequest(String username, String password) {}
record RegisterRequest(String username, String email, String password) {}

record AuthenticationResponse(String token) {
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    static class AuthenticationResponseBuilder {
        private String token;

        AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        AuthenticationResponse build() {
            return new AuthenticationResponse(token);
        }
    }
}
