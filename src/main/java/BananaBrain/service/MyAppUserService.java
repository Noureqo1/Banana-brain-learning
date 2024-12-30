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

    public MyAppUser save(MyAppUser user) {
        return repository.save(user);
    }
}