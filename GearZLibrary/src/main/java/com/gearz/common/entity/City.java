package com.gearz.common.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "districts")
public class City extends BaseEntity {

	@Column(length = 30, unique = true, nullable = false)
	@Getter
	@Setter
	private String name;

	@Column(length = 3, unique = true, nullable = false)
	@Getter
	@Setter
	private String code;

	@OneToMany(mappedBy = "city")
	private Set<District> districts;
}
