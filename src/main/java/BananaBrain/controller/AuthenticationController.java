package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.security.JwtService;
import BananaBrain.service.MyAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final MyAppUserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {
        try {

            // Create and save new user
            MyAppUser newUser = new MyAppUser();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(password));

            userService.save(newUser);

            // Add success message
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/signup";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyAppUser user = (MyAppUser) authentication.getPrincipal();
            return "redirect:/courses";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }
    // For API clients
    @RestController
    @RequestMapping("/api/auth")
    public static class AuthenticationRestController {
        private final AuthenticationManager authenticationManager;
        private final MyAppUserService userService;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;

        public AuthenticationRestController(
                AuthenticationManager authenticationManager,
                MyAppUserService userService,
                JwtService jwtService,
                PasswordEncoder passwordEncoder) {
            this.authenticationManager = authenticationManager;
            this.userService = userService;
            this.jwtService = jwtService;
            this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/login")
        public ResponseEntity<Map<String, String>> apiLogin(@RequestBody Map<String, String> loginRequest) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.get("username"),
                                loginRequest.get("password")
                        )
                );
                MyAppUser user = (MyAppUser) authentication.getPrincipal();
                String token = jwtService.generateToken(user);
                return ResponseEntity.ok(Map.of("token", token));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
            }
        }

        @PostMapping("/register")
        public ResponseEntity<?> apiRegister(@RequestBody Map<String, String> registrationRequest) {
            try {
                String username = registrationRequest.get("username");
                String password = registrationRequest.get("password");
                String email = registrationRequest.get("email");

                if (username == null || password == null || email == null) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Username, password, and email are required"));
                }


                MyAppUser newUser = new MyAppUser();
                newUser.setUsername(username);
                newUser.setEmail(email);
                newUser.setPassword(passwordEncoder.encode(password));

                userService.save(newUser);

                return ResponseEntity.ok(Map.of("message", "User registered successfully"));
            } catch (Exception e) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Registration failed: " + e.getMessage()));
            }
        }
    }
}
