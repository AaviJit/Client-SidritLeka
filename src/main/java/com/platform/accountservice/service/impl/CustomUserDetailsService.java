package com.platform.accountservice.service.impl;

import com.platform.accountservice.domain.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Autowired
    public CustomUserDetailsService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("================ Username ================");
        System.out.println(username);
        System.out.println("==========================================");
        User user = null;

        try {
            user = this.userService.findUserByUsername(username);
        } catch (Exception e) {
            System.out.println("=================User not found exception==================");
            e.printStackTrace();
        }

        if (user == null) {
            System.out.println("=================User is null====================");
            throw new UsernameNotFoundException("User is not registered");
        }

        return user;
    }
}