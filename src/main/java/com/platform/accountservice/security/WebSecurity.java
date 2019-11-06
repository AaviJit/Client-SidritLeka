/*
package com.platform.accountservice.security;

import java.util.Collections;

import com.platform.accountservice.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.platform.accountservice.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public WebSecurity(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }




  */
/*  @Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean()
			throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); //  frontend client 
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*//*




    @Override
    protected void configure(HttpSecurity http) throws Exception {
       */
/* http
                .authorizeRequests()

                *//*
*/
/*.antMatchers("/","/users/register","/registrationConfirm").permitAll()
                .antMatchers("/users/user/**", "/users/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER","ROLE_MODERATOR")
                .antMatchers("/users/allUsers","/users/changeRole","/users/changePassword","/resetPassword","/savePassword").hasAnyAuthority("ROLE_ADMIN")*//*
*/
/*
                .antMatchers(HttpMethod.PUT, SecurityConstants.CHANGE_USER_ROLE_URL, SecurityConstants.CHANGE_USER_PASSWORD_URL).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, SecurityConstants.GET_USER_URL).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, SecurityConstants.UPDATE_USER_URL).hasAnyRole("USER", "ADMIN")
        		.antMatchers(HttpMethod.GET, SecurityConstants.ADMIN_URL).hasRole("ADMIN")
        		.antMatchers(HttpMethod.GET, SecurityConstants.MODERATOR_URL).hasAnyRole("MODERATOR")
        ;
     // Custom JWT based authentication
     		http.addFilterBefore(authenticationTokenFilterBean(),
     				UsernamePasswordAuthenticationFilter.class);*//*




        http
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()

                .antMatchers("/api-docs/**").permitAll()
                .antMatchers(HttpMethod.PUT, SecurityConstants.CHANGE_USER_ROLE_URL, SecurityConstants.CHANGE_USER_PASSWORD_URL).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, SecurityConstants.GET_USER_URL).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, SecurityConstants.UPDATE_USER_URL).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, SecurityConstants.ADMIN_URL).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, SecurityConstants.MODERATOR_URL).hasAnyRole("MODERATOR")
                .antMatchers(HttpMethod.GET, SecurityConstants.GET_ALL_USER_URL).hasAnyRole("ADMIN")

                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
        ;
        */
/*http.addFilterBefore(authenticationTokenFilterBean(),
                UsernamePasswordAuthenticationFilter.class);*//*

    }

    


}
*/
