package com.gearz.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage extends BaseEntity {

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Transient
    public String getImagePath() {
        return "/product-images/" + product.getId() + "/extra-images/" + this.image;
    }

    public ProductImage(Integer id, String image, Product product) {
        super();
        this.id = id;
        this.image = image;
        this.product = product;
    }
}
