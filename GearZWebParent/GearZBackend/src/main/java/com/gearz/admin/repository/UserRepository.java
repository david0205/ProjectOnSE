package com.gearz.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gearz.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	
	public Long countById(Integer id);
	
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateUserStatus(Integer id, boolean enabled);
}
