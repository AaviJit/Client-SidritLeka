package com.platform.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.accountservice.domain.User;
import com.platform.accountservice.domain.VerificationToken;

public interface VerificationTokenRepository 
extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);
}

