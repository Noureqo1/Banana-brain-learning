package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.model.Roles;
import BananaBrain.repository.MyAppUserRepository;
import BananaBrain.repository.RoleRepository;
import BananaBrain.service.MyAppUserService;
import BananaBrain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegistrationController {

    @Autowired
    private MyAppUserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/req/signup")
    public String createUser(@ModelAttribute MyAppUser user, RedirectAttributes redirectAttributes) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyAppUser savedUser = myAppUserRepository.save(user);

        // Add user ID to redirect attributes
        redirectAttributes.addAttribute("userId", savedUser.getId());

        // Redirect to the role selection page with userId as a query parameter
        return "redirect:/login";
    }

    @PostMapping("/assignRole")
    public ResponseEntity<String> addRole(@RequestParam("role") String roleName) {
        Roles role = new Roles();
        role.setRole(roleName);
        roleService.saveRole(role);
        return ResponseEntity.ok("Role added successfully");
    }
}

