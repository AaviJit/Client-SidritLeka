package com.platform.accountservice.service;

import com.platform.accountservice.domain.PasswordResetToken;

public interface SecurityService {
	
	String validatePasswordResetToken(Integer id, String token);
	
	PasswordResetToken getPasswordResetToken(String token);

}
