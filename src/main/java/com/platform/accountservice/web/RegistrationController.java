package com.platform.accountservice.web;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.accountservice.domain.User;
import com.platform.accountservice.domain.VerificationToken;
import com.platform.accountservice.service.UserService;

@RestController
@RequestMapping("registrationConfirm")
public class RegistrationController {
	@Autowired
	private UserService service;
	 
	@GetMapping
	public String confirmRegistration(@RequestParam("token") String token) {
	 
	     
	    VerificationToken verificationToken = service.getVerificationToken(token);
	    if (verificationToken == null) {
	        return "Invalid Token";
	    }
	     
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        return "Invalid Token";
	    } 
	     
	    user.setIsEnabled(true);
	    service.saveRegisteredUser(user); 
	    return "Email Verification is successful";
	}
}
