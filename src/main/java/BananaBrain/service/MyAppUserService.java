package BananaBrain.service;

import java.util.Optional;

import BananaBrain.model.MyAppUser;
import BananaBrain.model.Roles;
import BananaBrain.repository.MyAppUserRepository;
import BananaBrain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyAppUserService implements UserDetailsService{

    @Autowired
    private MyAppUserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MyAppUser> user = repository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
    public void assignRoleToUser(Long userId, String roleName) {
        // Fetch user by ID
        MyAppUser user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch role by name
        Roles role = roleRepository.findByrole(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Add role to user
        user.getRoles().add(role);

        // Save updated user
        repository.save(user);
    }
}