package com.techstore.admin.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new TechStoreUserDetailSevice();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/users/**","/settings/**").hasAuthority("Admin")
		.antMatchers("/categories/**","/brands/**","/menus/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/products/**").hasAnyAuthority("Admin", "Salesperson", "Editor", "Shipper")
		.antMatchers("/customers/**","/shipping/**","/articles/**").hasAnyAuthority("Admin", "Salesperson")
		.antMatchers("/orders/**","/report/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
		.anyRequest()
		.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll()
				.and()
				.logout()
				.permitAll()
				.and()
				.rememberMe()
				.key("AbcDefgKLDSLmvop_0123456789")
				.tokenValiditySeconds(7 * 24 * 60 * 60); // 7 days 24 hours 60 minutes 60 seconds -> 7days 
				;
	}

	// Before authenticated, all matchers can be ignored and all these are
	// performed.
	// Like -> showing image in /login , all images,js and web jars files are
	// detected
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
}
