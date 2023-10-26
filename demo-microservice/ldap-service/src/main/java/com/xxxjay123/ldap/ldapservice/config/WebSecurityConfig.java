package com.xxxjay123.ldap.ldapservice.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
/*    @Bean
 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http//
			.authorizeRequests()//
				.anyRequest().fullyAuthenticated()//
				.and()//
			.formLogin();//

		return http.build();//
	}//
 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()//
				.userDnPatterns("uid={0},ou=people")//
				.groupSearchBase("ou=groups")//
				.groupSearchFilter("member={0}")//
				.groupRoleAttribute("cn")//
				.rolePrefix("ROLE_")
				.contextSource()//
					.url("ldap://localhost:8389/dc=springframework,dc=org")//
					.and()//
				.passwordCompare()//
					.passwordEncoder(new BCryptPasswordEncoder())//
					.passwordAttribute("userPassword");//
	}

}


