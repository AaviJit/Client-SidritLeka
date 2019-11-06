package com.platform.accountservice.domain.dto;

public class PasswordChangeDTO {
	private String username;
	private String password;
	
	public PasswordChangeDTO(String username, String password) {
		
		this.username = username;
		this.password = password;
	}
	
	public PasswordChangeDTO() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
