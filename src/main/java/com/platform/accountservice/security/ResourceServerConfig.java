//package com.platform.accountservice.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.securecurity.oauth2.config.annotation.web.configuration.EnableResourceServer;
////import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
////impoity.config.BeanIds;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.srt org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//	private static final String RESOURCE_ID = "resource_id";
//	
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) {
//		resources.resourceId(RESOURCE_ID).stateless(false);
//	}
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.
//		anonymous().disable()
//		.authorizeRequests()
//		//.antMatchers(HttpMethod.GET, SecurityConstants.EMAIL_VERIFICATION_URL).anonymous()
//		//.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL, SecurityConstants.RESET_PASSWORD_URL, SecurityConstants.SAVE_PASSWORD_URL).permitAll()
//		.antMatchers(HttpMethod.GET, SecurityConstants.GET_USER_URL).hasRole("USER")
//		.antMatchers(HttpMethod.GET, SecurityConstants.ADMIN_URL).hasRole("ADMIN")
//		.antMatchers(HttpMethod.GET, SecurityConstants.MODERATOR_URL).hasAnyRole("MODERATOR")
//		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//	}
//
//}
