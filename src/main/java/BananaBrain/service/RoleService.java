package BananaBrain.service;

import BananaBrain.model.Roles;
import BananaBrain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    public void saveRole(Roles role) {
        roleRepository.save(role);
    }
    public Optional<Roles> findByRoleName(String roleName)
    {
        return roleRepository.findByRole(roleName);
    }
}
