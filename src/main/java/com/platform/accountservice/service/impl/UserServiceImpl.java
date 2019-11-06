package com.platform.accountservice.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.platform.accountservice.domain.PasswordResetToken;
import com.platform.accountservice.domain.Permission;
import com.platform.accountservice.domain.Role;
import com.platform.accountservice.domain.User;
import com.platform.accountservice.domain.VerificationToken;
import com.platform.accountservice.domain.dto.ChangeRoleDTO;
import com.platform.accountservice.domain.dto.PasswordChangeDTO;
import com.platform.accountservice.domain.dto.UserDTO;
import com.platform.accountservice.exceptions.RecordExistsException;
import com.platform.accountservice.exceptions.RecordNotFoundException;
import com.platform.accountservice.repository.PasswordTokenRepository;
import com.platform.accountservice.repository.RoleRepository;
import com.platform.accountservice.repository.UserRepository;
import com.platform.accountservice.repository.VerificationTokenRepository;
import com.platform.accountservice.security.TokenUtils;
import com.platform.accountservice.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RecordExistsException("Record with given email already exists!");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RecordExistsException("Record with given username already exists!");
        }

//        return userRepository.saveAndFlush(user);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null)
            throw new UsernameNotFoundException(username);
        if (!userEntity.getIsEnabled())
            throw new RuntimeException("Please verify your email before login!");

        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getIsEnabled(),
                true, true, true, getAuthorities(userEntity.getUserRoles()));
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(
//      Collection<Roles> roles) {
//  
//        return getGrantedAuthorities(getPrivileges(roles));
//    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            role.getRolePermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getPermissionName()))
                    .forEach(authorities::add);
        }

        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getRolePermissions());
        }
        for (Permission item : collection) {
            privileges.add(item.getPermissionName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
//        VerificationToken myToken = new VerificationToken(user, token);
        //myToken.setUser(user);
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUser(user);
        tokenRepository.save(myToken);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(user, token);
        passwordTokenRepository.save(myToken);
    }

    public void changeUserPassword(User user) {
        userRepository.save(user);
    }

    @Override
    public void changeRole(ChangeRoleDTO changeRoleDTO) {
        User user = userRepository.findByUsername(changeRoleDTO.getUsername());

        if (user == null) {
            throw new RecordNotFoundException("User with given username not found!");
        }

        List<Role> roles = new ArrayList<Role>();
        for (String roleName : changeRoleDTO.getRoles()) {
            Role role = roleRepository.findByRoleName(roleName);

            if (role == null) {
                throw new RecordNotFoundException("Role with name " + roleName + " not found!");
            }

            if (roles.contains(role)) {
                throw new RecordExistsException("Role with name " + roleName + " already specified!");
            }

            roles.add(role);
        }
        user.setUserRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void updateUser(String username, UserDTO userDTO, String token) {
        /*String loggedUser = tokenUtils.getUsernameFromToken(token);
        if (!loggedUser.equals(username)) {
            throw new RuntimeException("Access denied!");
        }*/

        if (userDTO == null || !userDTO.isValid()) {
            throw new RuntimeException("Invalid data format! All fields must be filled.");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RecordNotFoundException("Record with given username not found!");
        }


        List<Role> roles = user.getUserRoles();

        boolean isAdmin = false;
        for (Role role : roles) {
            if (role.getRoleName().equalsIgnoreCase("ROLE_ADMIN"))
                isAdmin = true;
            break;
        }


        if (isAdmin) {
            if (userDTO.getPassword() != null) {
                user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            } else {
                user.setPassword(user.getPassword());
            }
            if (userDTO.getRoles() != null) {
                for (String roleName : userDTO.getRoles()) {
                    Role role1 = roleRepository.findByRoleName(roleName);
                    if (role1 == null) {
                        throw new RecordNotFoundException("Role with name " + roleName + " not found!");
                    }
                    if (roles.contains(role1)) {
                        throw new RecordExistsException("Role with name " + roleName + " already specified!");
                    }
                    roles.add(role1);
                }
                user.setUserRoles(roles);

            } else {
                user.setUserRoles(user.getUserRoles());
            }

            if (!userDTO.getEmail().equalsIgnoreCase(user.getEmail()) && userRepository.findByEmail(userDTO.getEmail()) == null) {
                user.setEmail(userDTO.getEmail());
            } else {
                throw new RecordExistsException("User exist with this email");
            }

            if (!userDTO.getUsername().equals(user.getUsername()) && userRepository.findByUsername(userDTO.getUsername()) == null) {
                user.setUsername(userDTO.getUsername());
            } else {
                throw new RecordExistsException("User exist with this username");
            }
        }

        user.getUserProfiles().setFirstname(userDTO.getFirstname());
        user.getUserProfiles().setLastname(userDTO.getLastname());
        userRepository.save(user);

    }

    @Override
    public void changeUserPassword(PasswordChangeDTO passwordChangeDTO) {
        User user = userRepository.findByUsername(passwordChangeDTO.getUsername());

        if (user == null) {
            throw new RecordNotFoundException("Record with given username not found!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordChangeDTO.getPassword()));
        userRepository.save(user);

    }


}
