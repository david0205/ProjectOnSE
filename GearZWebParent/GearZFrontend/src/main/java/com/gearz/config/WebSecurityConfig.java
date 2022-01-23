package com.gearz.config;

import com.gearz.security.CustomerUserDetailsService;
import com.gearz.security.DatabaseLoginSuccessHandler;
import com.gearz.security.oauth.CustomerOAuth2UserService;
import com.gearz.security.oauth.OAuth2LoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomerOAuth2UserService oAuth2UserService;

	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginHandler;

	@Autowired
	private DatabaseLoginSuccessHandler databaseLoginHandler;

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean 
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/img/**", "/js/**", "/css/**", "/webjars/**").permitAll()
			.antMatchers("/profile", "/update_profile", "/cart", "/amount", "/cart-items", "/address_book/**",
				 "/checkout", "/place_order", "process_paypal_order", "/orders/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginPage("/account")
				.usernameParameter("customer_email")
				.successHandler(databaseLoginHandler)
				.permitAll()
			.and()
			.oauth2Login()
				.loginPage("/account")
				.userInfoEndpoint()
				.userService(oAuth2UserService)
				.and()
				.successHandler(oAuth2LoginHandler)
			.and()
			.logout().permitAll()
			.and()
			.rememberMe()
				.key("yxwwh2NG7e5sZ3rALeqzktA7HpaN6y")
				.tokenValiditySeconds(14 * 24 * 60 * 60);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}

}
