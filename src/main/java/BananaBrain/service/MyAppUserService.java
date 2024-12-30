package BananaBrain.service;

import BananaBrain.model.MyAppUser;

import BananaBrain.repository.MyAppUserRepository;
import BananaBrain.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public MyAppUser save(MyAppUser user) {
        if (user.getId() != null) {
            // This is an existing user, just merge the changes
            return repository.save(user);
        }
        // This is a new user, check if username exists
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return repository.save(user);
    }
}