package com.platform.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.accountservice.domain.PasswordResetToken;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
	
}

