package com.gearz.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Product extends BaseEntity {

	@NonNull
	@Column(length = 256, unique = true, nullable = false)
	private String name;

	@NonNull
	@Column(length = 64, unique = true, nullable = false)
	private String tag;

	@NonNull
	@Column(length = 512, nullable = false)
	private String shortDescription;

	@NonNull
	@Column(length = 4096, nullable = false)
	private String fullDescription;

	private Date createdTime;

	private Date updatedTime;

	private boolean enabled;

	@Column
	private boolean inStock;

	private float price;

	@Column
	private float discountPercentage;

	private float length;

	private float width;

	private float height;

	private float weight;

	@Column(nullable = false)
	private String mainImage;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductImage> images = new HashSet<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductSpecification> specs = new ArrayList<>();

	public void addExtraImage(String extra_image) {
		this.images.add(new ProductImage(extra_image, this));
	}

	public void addSpecifications(String name, String value) {
		this.specs.add(new ProductSpecification(name, value, this));
	}

	public void addSpecifications(Integer id, String name, String value) {
		this.specs.add(new ProductSpecification(id, name, value, this));
	}

	@Transient
	public String getMainImagePath() {
		if (this.id != null && this.mainImage.isEmpty()) {
			return "/img/no-image-icon.png";
		}
		if (this.id == null || this.mainImage == null) {
			return "/img/no-image-icon.png";
		}
		return "/product-images/" + this.id + "/" + this.mainImage;
	}

	public boolean containsImageName(String imageName) {
		Iterator<ProductImage> iterator = images.iterator();
		while (iterator.hasNext()) {
			ProductImage image = iterator.next();
			if (image.getImage().equals(imageName)) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public String getShortName() {
		if (name.length() > 30) {
			return name.substring(0, 30).concat("...");
		}
		return name;
	}

	@Transient
	public float getDiscountedPrice() {
		if (discountPercentage > 0) {
			return price * ((100 - discountPercentage) / 100);
		}
		return this.price;
	}

	public Product(int productId) {
		this.id = productId;
	}
}
