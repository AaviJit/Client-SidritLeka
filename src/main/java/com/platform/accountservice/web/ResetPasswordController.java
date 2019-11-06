package com.platform.accountservice.web;

import java.util.UUID;

import javax.servlet.ServletContext;

import com.platform.accountservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.accountservice.domain.dto.PasswordChangeDTO;
import com.platform.accountservice.domain.dto.PasswordResetDTO;
import com.platform.accountservice.domain.dto.PasswordSaveDTO;
import com.platform.accountservice.service.SecurityService;
import com.platform.accountservice.service.UserService;

@RestController
public class ResetPasswordController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private JavaMailSender mailSender;
	
    @Autowired
    private ServletContext context;

	@Autowired
	private MessageSource messages;
	
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	@PostMapping
	@RequestMapping("resetPassword")
	public String resetPassword(@RequestBody PasswordResetDTO passwordResetRequest) {
	    User user = service.findUserByEmail(passwordResetRequest.getEmail());

	    String token = UUID.randomUUID().toString();
	    service.createPasswordResetTokenForUser(user, token);
	    
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String message = messages.getMessage("message.changePassword", null, LocaleContextHolder.getLocale());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " http://localhost:4200/reset-password?token=" + token + "&id=" + user.getId());
		mailSender.send(email);
		
		return "Email with reset Token sent";
	}

	@PostMapping
	@RequestMapping("savePassword")
	public String savePassword(@RequestBody PasswordSaveDTO passwordSaveRequest) {
	    String result = securityService.validatePasswordResetToken(passwordSaveRequest.getId(), passwordSaveRequest.getToken());
	    if (result != null) {
	    	return result;
	    }
	    
	    User user = securityService.getPasswordResetToken(passwordSaveRequest.getToken()).getUser();

	    user.setPassword(bCryptPasswordEncoder.encode(passwordSaveRequest.getPassword()));
	    
	    service.changeUserPassword(user);
	    
	    return "New Password Saved Successfully!";
	}
	
	

	

}
