package com.platform.accountservice.service;

import com.platform.accountservice.domain.Role;
import com.platform.accountservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Load User Roles
        if (roleRepository.findByRoleId(1) == null)
            roleRepository.save(new Role(1,"ROLE_ADMIN", "All Access"));
        else if (!roleRepository.findByRoleId(1).getRoleName().equals("ROLE_ADMIN")) {
            Role oldAdminDate = roleRepository.findByRoleId(1);
            Role role = new Role();
            role.setRoleId(oldAdminDate.getRoleId());
            role.setRoleName("ROLE_ADMIN");
            role.setRoleDescription("All Access");
            roleRepository.save(role);
        }


        if (roleRepository.findByRoleId(2) == null)
            roleRepository.save(new Role(2,"ROLE_USER", "Limited Access"));
        else if (!roleRepository.findByRoleId(2).getRoleName().equals("ROLE_USER")) {
            Role oldAdminDate = roleRepository.findByRoleId(2);
            Role role = new Role();
            role.setRoleId(oldAdminDate.getRoleId());
            role.setRoleName("ROLE_USER");
            role.setRoleDescription("Limited Access");
            roleRepository.save(role);
        }


//        if (roleRepository.findById(2) == null)
//            roleRepository.save(new Role(2,"ROLE_EMPLOYEE"));
//        else if (!roleRepository.findById(2).getName().equals("ROLE_EMPLOYEE")) {
//            Role oldAdminDate = roleRepository.findById(2);
//            Role role = new Role();
//            role.setId(oldAdminDate.getId());
//            role.setName("ROLE_EMPLOYEE");
//            roleRepository.save(role);
//        }

    }
}