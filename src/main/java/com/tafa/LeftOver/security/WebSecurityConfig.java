package com.tafa.LeftOver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tafa.LeftOver.services.UserService;




@Configuration
@EnableMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
//	
//	 @Override
//	    protected void configure(HttpSecurity http) throws Exception {
//		 http.csrf(csrf -> csrf.disable()).cors().and()
//         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//         .authorizeHttpRequests(auth -> auth.antMatchers("/rest/api/login/**").permitAll()
//         		
//         .antMatchers("/rest/api/user/**").permitAll()
//         .antMatchers("/login").permitAll()
//         .anyRequest().permitAll());  // Disable basic authentication if not needed
//	    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .cors()
	        .and()
	        .authorizeRequests()  // Change from authorizeHttpRequests() to authorizeRequests()
	        .antMatchers("/rest/api/login/**", "/rest/api/user/**", "/login").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .formLogin()
	        .loginProcessingUrl("/login")  // Spring Security handles login
	        .defaultSuccessUrl("/user/profile", true)
	        .permitAll()
	        .and()
	        .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/login?logout")
	        .invalidateHttpSession(true)
	        .deleteCookies("JSESSIONID")
	        .permitAll();
	}



	   


}
