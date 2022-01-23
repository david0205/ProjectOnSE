package com.gearz.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Category extends BaseEntity {

	@NonNull
	@Column(length = 128, unique = true, nullable = false)
	private String name;

	@NonNull
	@Column(length = 64, unique = true, nullable = false)
	private String tag;

	@NonNull
	@Column(length = 128, nullable = false)
	private String image;

	private boolean enabled;

	@Column(name = "all_parent_ids")
	private String allParentIDs;

	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	@OrderBy("id asc")
	private final Set<Category> children = new HashSet<>();

	@Transient
	private boolean hasChildren;

	public Category(@NonNull String name, @NonNull String tag, @NonNull String image, Category parent) {
		this(name, tag, image);
		this.parent = parent;
	}

	public static Category getCategory(Category category, String name) {
		Category category1 = new Category();
		category1.setId(category.getId());
		category1.setName(name);
		category1.setTag(category.getTag());
		category1.setEnabled(category.isEnabled());
		category1.setHasChildren(category.getChildren().size() > 0);
		return category1;
	}

	public static Category getIdAndName(Category category) {
		Category category1 = new Category();
		category1.setId(category.getId());
		category1.setName(category.getName());
		return category1;
	}

	public static Category getIdAndName(Integer id, String name) {
		Category category1 = new Category();
		category1.setId(id);
		category1.setName(name);
		return category1;
	}

	@Transient
	public String getImagePath() {
		if (this.id != null && this.image.isEmpty()) {
			return "/img/no-image-icon.png";
		}
		return "/category-images/" + this.id + "/" + this.image;
	}
}
