package com.platform.accountservice.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.accountservice.domain.PasswordResetToken;
import com.platform.accountservice.repository.PasswordTokenRepository;
import com.platform.accountservice.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	PasswordTokenRepository passwordTokenRepository;

	@Override
	public String validatePasswordResetToken(Integer id, String token) {
		PasswordResetToken passToken = 
				passwordTokenRepository.findByToken(token);
		if ((passToken == null) || (passToken.getUser()
				.getId() != id)) {
			return "invalid Token!";
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate()
				.getTime() - cal.getTime()
				.getTime()) <= 0) {
			return "expired Token!";
		}
		
		return null;
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		
		return passwordTokenRepository.findByToken(token);
	}

}
