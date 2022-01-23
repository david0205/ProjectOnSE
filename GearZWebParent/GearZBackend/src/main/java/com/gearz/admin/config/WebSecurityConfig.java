package com.gearz.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gearz.admin.security.UserDetailsSecurityService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsSecurityService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/img/**", "/js/**", "/css/**", "/webjars/**").permitAll()
			.antMatchers("/district/list_by_city/**", "/wards/list_by_district/**").hasAnyAuthority("Admin", "Salesperson")
			.antMatchers("/users/**", "/settings/**", "/cities/**", "/districts/**").hasAuthority("Admin")
			.antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
			.antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
			.antMatchers("/products/edit/**", "/products/save", "/products/check_product").hasAnyAuthority("Admin", "Editor", "Salesperson")
			.antMatchers("/products", "/products/", "/products/details/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
			.antMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
			.antMatchers("/orders", "/orders/", "/orders/details/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
			.antMatchers("/orders_shipper/update/**").hasAuthority("Shipper")
			.antMatchers("/orders/**", "/customers/**", "/get_shipping_cost").hasAnyAuthority("Admin", "Salesperson")
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll()
			.and().logout().permitAll()
			.and()
				.rememberMe()
					.tokenValiditySeconds(7 * 24 * 60 * 60) // expiration time: 7 days
				    .key("AbcdefghiJklmNoPqRstUvXyz");   // cookies will survive if restarted

		http.headers().frameOptions().sameOrigin();
	}
	
}
