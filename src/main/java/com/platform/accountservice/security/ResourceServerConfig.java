/*package com.platform.accountservice.security;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

   *//* @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/all/**", "/customers/**").permitAll()
                .antMatchers("/rider/**").hasAnyAuthority(Constants.Roles.ROLE_SUPER_ADMIN, Constants.Roles.ROLE_ADMIN, Constants.Roles.ROLE_ADMIN_LOCAL, Constants.Roles.ROLE_ADMIN_INTERNATIONAL, Constants.Roles.ROLE_AGENT, Constants.Roles.ROLE_RIDER, Constants.Roles.ROLE_ACCOUNT)
                .antMatchers("/employee/**").hasAnyAuthority(Constants.Roles.ROLE_SUPER_ADMIN, Constants.Roles.ROLE_ADMIN, Constants.Roles.ROLE_ADMIN_LOCAL, Constants.Roles.ROLE_ADMIN_INTERNATIONAL, Constants.Roles.ROLE_AGENT, Constants.Roles.ROLE_EMPLOYEE, Constants.Roles.ROLE_RIDER, Constants.Roles.ROLE_ACCOUNT)
                .antMatchers("/customer/**").hasAnyAuthority(Constants.Roles.ROLE_SUPER_ADMIN, Constants.Roles.ROLE_ADMIN, Constants.Roles.ROLE_ADMIN_LOCAL, Constants.Roles.ROLE_ADMIN_INTERNATIONAL, Constants.Roles.ROLE_CUSTOMER, Constants.Roles.ROLE_MERCHANT, Constants.Roles.ROLE_ACCOUNT)
                .antMatchers("/admin/**").hasAnyAuthority(Constants.Roles.ROLE_SUPER_ADMIN, Constants.Roles.ROLE_ADMIN, Constants.Roles.ROLE_ADMIN_LOCAL, Constants.Roles.ROLE_ADMIN_INTERNATIONAL, Constants.Roles.ROLE_ACCOUNT)
                .antMatchers("/agent/**").hasAnyAuthority(Constants.Roles.ROLE_SUPER_ADMIN, Constants.Roles.ROLE_ADMIN, Constants.Roles.ROLE_AGENT, Constants.Roles.ROLE_ADMIN_LOCAL, Constants.Roles.ROLE_ADMIN_INTERNATIONAL, Constants.Roles.ROLE_ACCOUNT)
                .antMatchers("/super-admin/**").hasRole("SUPER_ADMIN");
    }*//*


}*/
