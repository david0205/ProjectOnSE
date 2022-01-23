package com.gearz.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gearz.admin.repository.UserRepository;
import com.gearz.common.entity.User;

public class UserDetailsSecurityService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			return new UserDetailsSecurity(user);
		}
		throw new UsernameNotFoundException("This email does not exist!");
	}

}
