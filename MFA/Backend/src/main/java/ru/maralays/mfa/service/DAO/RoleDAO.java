package ru.maralays.mfa.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maralays.mfa.Entity.Role;
import ru.maralays.mfa.repository.RoleRepository;

import java.util.List;

@Service
public class RoleDAO {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoleList(){
        return roleRepository.findAll();
    }
}
