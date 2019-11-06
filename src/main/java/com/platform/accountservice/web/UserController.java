package com.platform.accountservice.web;


import com.platform.accountservice.domain.Profile;
import com.platform.accountservice.domain.User;
import com.platform.accountservice.domain.dto.ChangeRoleDTO;
import com.platform.accountservice.domain.dto.PasswordChangeDTO;
import com.platform.accountservice.domain.dto.UserDTO;
import com.platform.accountservice.repository.RoleRepository;
import com.platform.accountservice.security.TokenUtils;
import com.platform.accountservice.service.UserService;
import com.platform.accountservice.service.emailVerification.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
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

/*
    @PostMapping("/login")
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
    }*/

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUser() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/admin")
    public String getAdmin() {
        return "this is accessible only by admin";
    }


    @GetMapping("/moderator")
    public String getModerator() {
        return "this is accessible only by Moderator";
    }



    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDetails) {
        User user = new User();
        user.setEmail(userDetails.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        user.setUsername(userDetails.getUsername());
        user.setIsEnabled(true);
        user.setUserRoles(Collections.singletonList(roleRepository.findByRoleName("ROLE_USER")));

        Profile profile = new Profile();
        profile.setFirstname(userDetails.getFirstname());
        profile.setLastname(userDetails.getLastname());

        user.setProfile(profile);
        profile.setUser(user);
        User registered = userService.createUser(user);


        String appUrl = context.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, LocaleContextHolder.getLocale(), appUrl));

        /*Setting password null before sending response so that password won't be exposed*/
        registered.setPassword("");
        return new ResponseEntity<>(registered, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
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


    @PutMapping("/update/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        try {
            /*, @RequestHeader("X-Auth-Token") String token*/
            this.userService.updateUser(username, userDTO, null);
            return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/changeRole")
    public ResponseEntity<String> changeRole(@RequestBody ChangeRoleDTO changeRoleDTO) {

        try {
            this.userService.changeRole(changeRoleDTO);
            return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/changePassword")
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
