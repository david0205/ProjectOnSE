package com.gearz.common.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "districts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "city", "wards" })
public class District extends BaseEntity {

	@Column(length = 128, nullable = false)
	@Getter
	@Setter
	private String name;

	@OneToMany(mappedBy = "district")
	private Set<Ward> wards;

	@ManyToOne
	@JoinColumn(name = "city_id")
	@Getter
	@Setter
	private City city;
}
