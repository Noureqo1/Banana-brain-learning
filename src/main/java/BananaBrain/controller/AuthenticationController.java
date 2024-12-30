package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.repository.MyAppUserRepository;
import BananaBrain.security.JwtService;
import BananaBrain.service.MyAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    private final MyAppUserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/register", consumes = "application/json")
    public MyAppUser register(@ModelAttribute MyAppUser user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var userDetails = userService.loadUserByUsername(user.getUsername());
        var jwtToken = jwtService.generateToken(userDetails);
        AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return myAppUserRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            var userDetails = userService.loadUserByUsername(request.username());
            var jwtToken = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
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
