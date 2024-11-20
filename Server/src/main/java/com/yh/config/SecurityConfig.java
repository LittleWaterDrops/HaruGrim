package com.yh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 비밀번호 인코더 (BCrypt 사용)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 사용자 정의 UserDetailsService (예시로 InMemoryUserDetailsManager 사용)
	@Bean
	public UserDetailsService userDetailsService() {
	    User.UserBuilder userBuilder = User.withUsername("user")
	            .password(passwordEncoder().encode("password"))
	            .roles("USER");  // 사용자가 "USER" 권한을 가짐
	    return new InMemoryUserDetailsManager(userBuilder.build());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests()
	            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/users/auth/signup", "/users/auth/login").permitAll()
	            .requestMatchers(HttpMethod.GET, "/users/my").authenticated()
	            .anyRequest().authenticated()
	        .and()
	        .formLogin().permitAll()
	        .and()
	        .logout().permitAll()
	        .and()
	        .csrf().disable()
	        .sessionManagement()
	            .sessionFixation().newSession();
//	            .invalidSessionUrl("/login");
	    
	    return http.build();
	}


}
