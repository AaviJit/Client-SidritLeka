package com.platform.accountservice.domain.dto;

import java.util.List;

public class LoginConfirmedDTO {
	private String token;
	private List<String> roles;
	
	public LoginConfirmedDTO(String token, List<String> roles) {
		super();
		this.token = token;
		this.roles = roles;
	}
	
	public LoginConfirmedDTO() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
