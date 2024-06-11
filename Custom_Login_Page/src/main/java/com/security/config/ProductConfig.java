 package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ProductConfig {

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		/*
		 * // In memory security............... UserDetails admin =
		 * User.withUsername("Manju") .password(passwordEncoder.encode("Manju@123"))
		 * .roles("ADMIN") .build();
		 * 
		 * UserDetails user = User.withUsername("Swati")
		 * .password(passwordEncoder.encode("Swati@123")) .roles("USER") .build();
		 * return new InMemoryUserDetailsManager(admin, user);
		 */

		// Data base Security.....

		return new UserInfoUserDetailsService();

	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		/*
		 * http.csrf().disable() .authorizeHttpRequests()
		 * .requestMatchers("/product/welcome","/user/new",
		 * "/product/authenticate").permitAll() .and()
		 * .authorizeHttpRequests().requestMatchers("/product/**")
		 * .authenticated().and().formLogin(); return http.build();
		 */

		
		  http.csrf().disable()
		  .authorizeHttpRequests().requestMatchers("/product/welcome","/user/new","page/**").
		  permitAll() .requestMatchers("/product/**", "/data").authenticated().and()
		  .formLogin().loginPage("/signin").loginProcessingUrl("/userLogin").
		  permitAll().and().logout().logoutSuccessUrl("/userlogout").permitAll();
		  
		  return http.build();
		 

		
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
