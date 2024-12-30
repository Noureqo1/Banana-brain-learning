package BananaBrain.controller;

import BananaBrain.model.MyAppUser;
import BananaBrain.service.MyAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
@RequiredArgsConstructor
public class AssignRoleController {
    
    private final MyAppUserService userService;

    @GetMapping("/assignRole")
    public String showAssignRolePage(Model model, Authentication authentication) {
        MyAppUser user = (MyAppUser) authentication.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "role";
    }

    @PostMapping("/assignRole")
    public String assignRole(@RequestParam String selectedRole, Authentication authentication) {
        MyAppUser user = (MyAppUser) authentication.getPrincipal();
        userService.save(user);
        return "redirect:/courses";
    }
}
