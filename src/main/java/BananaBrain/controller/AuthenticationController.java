package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.security.JwtService;
import BananaBrain.service.MyAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

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

    @PostMapping("/login-form")
    public ModelAndView authenticateForm(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            var userDetails = userService.loadUserByUsername(username);
            var jwtToken = jwtService.generateToken(userDetails);

            // Get user role
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");

            // Redirect based on role
            if (role.equals("ROLE_STUDENT")) {
                return new ModelAndView("redirect:/studentHome");
            } else if (role.equals("ROLE_TEACHER")) {
                return new ModelAndView("redirect:/teacherHome");
            }

            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("redirect:/login?error=true");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getUserRole(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userService.loadUserByUsername(principal.getName());
        var role = user.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        return ResponseEntity.ok(Map.of("role", role));
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
