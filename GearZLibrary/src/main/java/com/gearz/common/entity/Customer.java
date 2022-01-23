package com.gearz.common.entity;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends BaseAddressLine {

	@Column(length = 128, unique = true, nullable = false)
	private String email;

	@Column(name = "password", length = 64, nullable = false)
	private String fullPassword;

	@Column(nullable = true)
	private String profilePic;

	@Column(length = 64)
	private String verificationCode;

	@Column(length = 30)
	private String resetPasswordToken;

	private boolean enabled;

	@Column(nullable = false)
	private Date createdTime;

	@ManyToOne
	@JoinColumn(name = "ward_id")
	private Ward ward;

	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@Enumerated(EnumType.STRING)
	@Column(name = "authentication_type", length = 10)
	private AuthenticationType authenticationType;

	@Transient
	public String getFullAddress() {
		String fullAddress = "";
		if (fullName != null && !fullName.isEmpty()) {
			fullAddress += "- <strong>" + fullName + "</strong>";
		}
		if (!addressLine.isEmpty()) {
			fullAddress += ", " + addressLine;
		}
		if (ward.getName() != null && !ward.getName().isEmpty()) {
			fullAddress += ", " + ward.getName();
		}
		if (district.getName() != null && !district.getName().isEmpty()) {
			fullAddress += ", " + district.getName();
		}
		if (city.getName() != null && !city.getName().isEmpty()) {
			fullAddress += ", " + city.getName() + ".<br>";
		}
		if (!phoneNumber.isEmpty()) {
			fullAddress += "- Telephone: " + phoneNumber;
		}

		return fullAddress;
	}

}
