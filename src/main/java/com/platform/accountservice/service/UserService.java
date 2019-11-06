package com.platform.accountservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.platform.accountservice.domain.User;
import com.platform.accountservice.domain.VerificationToken;
import com.platform.accountservice.domain.dto.ChangeRoleDTO;
import com.platform.accountservice.domain.dto.PasswordChangeDTO;
import com.platform.accountservice.domain.dto.UserDTO;

public interface UserService extends UserDetailsService {

    User createUser(User user);

    User getUser(String verificationToken);
    
    List<User> getAllUsers();

    User findUserByEmail(String userEmail);
    
    User findUserByUsername(String username);
    
    void saveRegisteredUser(User user);
 
    void createVerificationToken(User user, String token);
 
    VerificationToken getVerificationToken(String VerificationToken);

	void createPasswordResetTokenForUser(User user, String token);

	void changeUserPassword(User user);
	
	void changeUserPassword(PasswordChangeDTO passwordChangeDTO);
	
	void changeRole(ChangeRoleDTO changeRoleDTO);
	
	void updateUser(String username, UserDTO userDTO, String token);
}
