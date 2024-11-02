package com.kishor.learn.spring.security.config;

import java.net.http.HttpRequest;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class BasicAuthSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.anyRequest().authenticated();
		}); // authenticate any requests .

		http.sessionManagement(session -> {
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}); // to makes session as stateless .

		http.headers((headers) -> {
			headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
		});

		http.httpBasic();
		http.csrf().disable(); // to disable csrf token creation
		return http.build();

	}

	/*
	 * @Bean public UserDetailsService userDetailsService() {
	 * 
	 * var user =
	 * User.withUsername("In28Minutes").password("{noop}dummy").roles("USER").build(
	 * );
	 * 
	 * var admin =
	 * User.withUsername("admin").password("{noop}dummy").roles("ADMIN").build();
	 * 
	 * return new InMemoryUserDetailsManager(user, admin); }
	 */
	
	@Bean
	public DataSource dataSource()
	{
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {

		var user = User.withUsername("In28Minutes")
				//.password("{noop}dummy")
				.password("dummy")
				.passwordEncoder((str-> passwordEncoder().encode(str)))
				.roles("USER")
				.build();

		var admin = User.withUsername("admin")
				//.password("{noop}dummy")
				.password("admin")
				.passwordEncoder((str-> passwordEncoder().encode(str)))
				.roles("ADMIN")
				.build();

		var jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

}
