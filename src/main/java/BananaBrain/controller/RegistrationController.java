package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.model.Roles;
import BananaBrain.repository.MyAppUserRepository;
import BananaBrain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup")
    public String createUser(@ModelAttribute MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        myAppUserRepository.save(user);
        return "redirect:/role";
    }

    @PostMapping(value = "/role/assign")
    public String assignRole(@ModelAttribute("username") String username, @ModelAttribute("role") String roleName) {
        MyAppUser user = myAppUserRepository.findByUsername(username).orElseThrow();
        Roles role = roleRepository.findByRole(roleName).orElseThrow();
        user.setRole(role);
        myAppUserRepository.save(user);
        return "redirect:/login"; // Redirect to login page
    }
}