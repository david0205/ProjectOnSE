package com.gearz.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gearz.admin.exceptions.UserNotFoundException;
import com.gearz.admin.repository.RoleRepository;
import com.gearz.admin.repository.UserRepository;
import com.gearz.common.entity.Role;
import com.gearz.common.entity.User;

@Service
@Transactional
public class UserService {

	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<User> listAllUsers() {
		return (List<User>) userRepo.findAll();
	}
	
	public List<Role> listAllRoles() {
		return (List<Role>) roleRepo.findAll(); 
	}
	
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public User save(User user) {
		boolean isUpdateUser = (user.getId() != null);		// true: update user mode, false: add new user mode 
		
		if (isUpdateUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		
		return userRepo.save(user);
	}
	
	public User updateAccount(User userInUpdate) {
		User userPostUpdate = userRepo.findById(userInUpdate.getId()).get();
		if (!userInUpdate.getPassword().isEmpty()) {
			userPostUpdate.setPassword(userInUpdate.getPassword());
			encodePassword(userPostUpdate);
		}
		if (userInUpdate.getProfilePic() != null) {
			userPostUpdate.setProfilePic(userInUpdate.getProfilePic());
		}
		
		userPostUpdate.setFirstName(userInUpdate.getFirstName());
		userPostUpdate.setLastName(userInUpdate.getLastName());
		
		return userRepo.save(userPostUpdate);
	}
	
	public boolean isEmailExisted(Integer id, String email) {
		User userFoundByEmail = userRepo.findByEmail(email);
		if (userFoundByEmail == null) {
			return true;
		}
		
		// There is a user that already had this email
		boolean isCreateNew = (id == null);
		if (isCreateNew) {
			if (userFoundByEmail != null) return false;
		} else {
			if (userFoundByEmail.getId() != id) return false;
		}
		
		return true;
	}

	public User getUserById(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();			
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("No user ID " + id + " was found");
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long count = userRepo.countById(id);
		if (count == null || count == 0) {
			throw new UserNotFoundException("No user ID " + id + " was found");
		}
		userRepo.deleteById(id);
	}
	
	public void changeUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateUserStatus(id, enabled);
	}
}
