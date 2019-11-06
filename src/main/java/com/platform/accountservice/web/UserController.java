package com.platform.accountservice.web;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.platform.accountservice.domain.Profile;
import com.platform.accountservice.domain.User;
import com.platform.accountservice.domain.dto.ChangeRoleDTO;
import com.platform.accountservice.domain.dto.LoginConfirmedDTO;
import com.platform.accountservice.domain.dto.PasswordChangeDTO;
import com.platform.accountservice.domain.dto.UserDTO;
import com.platform.accountservice.domain.dto.UserLoginDTO;
import com.platform.accountservice.repository.RoleRepository;
import com.platform.accountservice.security.TokenUtils;
import com.platform.accountservice.service.UserService;
import com.platform.accountservice.service.emailVerification.OnRegistrationCompleteEvent;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    ServletContext context;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO loginDTO) {
        try {
            // Perform the authentication
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload user details so we can generate token
            UserDetails details = userService.loadUserByUsername(loginDTO.getUsername());
            ArrayList<String> roles = new ArrayList<String>();
            details.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(roles::add);
            ;

            LoginConfirmedDTO retVal = new LoginConfirmedDTO(tokenUtils.generateToken(details), roles);
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid login", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @RequestMapping("/allUsers")
    public ResponseEntity<List<User>> getUser() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/admin")
    public String getAdmin() {
        return "this is accessible only by admin";
    }

    @GetMapping
    @RequestMapping("/moderator")
    public String getModerator() {
        return "this is accessible only by Moderator";
    }


    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDetails) {
        User user = new User();
        user.setEmail(userDetails.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        user.setUsername(userDetails.getUsername());
        user.setIsEnabled(false);
        user.setUserRoles(Collections.singletonList(roleRepository.findByRoleName("ROLE_USER")));

        Profile profile = new Profile();
        profile.setFirstname(userDetails.getFirstname());
        profile.setLastname(userDetails.getLastname());

        user.setProfile(profile);
        profile.setUser(user);
        User registered = userService.createUser(user);


        String appUrl = context.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, LocaleContextHolder.getLocale(), appUrl));


        return new ResponseEntity<>(registered, HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId) {
        List<User> users = userService.getAllUsers();
        User user = new User();

        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;

                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

    }

    @PutMapping
    @RequestMapping("/update/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO, @RequestHeader("X-Auth-Token") String token) {
        try {
            this.userService.updateUser(username, userDTO, token);
            return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @RequestMapping("/changeRole")
    public ResponseEntity<String> changeRole(@RequestBody ChangeRoleDTO changeRoleDTO) {

        try {
            this.userService.changeRole(changeRoleDTO);
            return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @RequestMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {

        try {
            this.userService.changeUserPassword(passwordChangeDTO);
            return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public String deleteUser() {
        return "deleteUser was called";
    }

}
