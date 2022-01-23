package com.gearz.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = { "logo", "categories" })
public class Brand extends BaseEntity {

	@NonNull
	@Column(length = 40, unique = true, nullable = false)
	private String name;

	@NonNull
	@Column(length = 128, nullable = false)
	private String logo;

	@ManyToMany
	@JoinTable(name = "brands_categories", joinColumns = @JoinColumn(name = "brand_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	@Transient
	public String getLogoPath() {
		if (this.id != null && this.logo.isEmpty()) {
			return "/img/no-image-icon.png";
		}
		return "/brand-logos/" + this.id + "/" + this.logo;
	}

	public Brand(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}
