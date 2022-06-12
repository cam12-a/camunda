package ru.maralays.mfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maralays.mfa.Entity.Role;
import ru.maralays.mfa.service.DAO.RoleDAO;

import java.util.List;

@RestController
@RequestMapping("api/roles/")
public class RolesAPI {


    @Autowired
    RoleDAO roleDAO;

    @CrossOrigin
    @GetMapping("role_list/")
    public List<Role> getAllRole(){
        return roleDAO.getRoleList();
    }
}
