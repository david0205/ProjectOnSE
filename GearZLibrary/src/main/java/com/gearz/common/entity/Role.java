package com.gearz.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "role_name", callSuper = true)
public class Role extends BaseEntity {

	@Column(name = "name", length = 40, unique = true, nullable = false)
	private String role_name;

	@Override
	public String toString() {
		return this.role_name;
	}
}
