package com.eshoppingzone.pojo;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public class UserProfile {
	private BigInteger profielId;
	private String fullName;
	private String image;
	private String email;
	private Long mobileNumber;
	private String about;
	private LocalDate dateOfBirth;
	private String gender;
	private String role;
	private String password;

	private List<Address> addresses;

	public UserProfile() {
		super();
	}

	public UserProfile(BigInteger profielId, String fullName, String image, String email, Long mobileNumber,
			String about, LocalDate dateOfBirth, String gender, String role, String password) {
		super();
		this.profielId = profielId;
		this.fullName = fullName;
		this.image = image;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.about = about;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.role = role;
		this.password = password;
	}

	public BigInteger getProfielId() {
		return profielId;
	}

	public void setProfielId(BigInteger profielId) {
		this.profielId = profielId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserProfile [profielId=" + profielId + ", fullName=" + fullName + ", image=" + image + ", email="
				+ email + ", mobileNumber=" + mobileNumber + ", about=" + about + ", dateOfBirth=" + dateOfBirth
				+ ", gender=" + gender + ", role=" + role + ", password=" + password + ", addresses=" + addresses + "]";
	}
}
