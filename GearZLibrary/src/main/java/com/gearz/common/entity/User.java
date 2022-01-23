package com.gearz.common.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = { "password", "profilePic", "enabled" })
public class User extends BaseEntity {
	@NonNull
	@Column(length = 128, unique = true, nullable = false)
	private String email;

	@NonNull
	@Column(length = 64, nullable = false)
	private String password;

	@NonNull
	@Column(length = 20, nullable = false)
	private String firstName;

	@NonNull
	@Column(length = 20, nullable = false)
	private String lastName;

	@Column(length = 64)
	private String profilePic;

	private boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Transient
	public String getImagePath() {
		if (this.id != null && this.profilePic.isEmpty()) {
			return "/img/user.jpg";
		}
		if (this.id == null || this.profilePic == null) {
			return "/img/user.jpg";
		}
		return "/user-profile-picture/" + this.id + "/" + this.profilePic;
	}

	public boolean hasRole(String roleName) {
		Iterator<Role> iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getRole_name().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
