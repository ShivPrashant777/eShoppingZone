package com.eshoppingzone.profile.UserProfileService.pojo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Entity
public class UserProfile {

//	@Transient
//	public static final String SEQUENCE_NAME = "user_profile_sequence";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int profileId;
	@NotBlank(message = "Username should not be empty")
	private String username;
	private String image;
	@Email(message = "Please provide a valid Email Address")
	private String email;
	private Long mobileNumber;
	@NotBlank(message = "About should not be empty")
	private String about;
	@Past(message = "Enter a valid Date of Birth")
	private LocalDate dateOfBirth;
	@NotBlank(message = "Gender should not be empty")
	private String gender;
	@Pattern(regexp = "Customer|Merchant", message = "Please select Customer or Merchant")
	private String role;
	@NotBlank(message = "Password should not be empty")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<Address> addresses;

	public UserProfile() {
		super();
	}

	public UserProfile(int profileId, String username, String image, String email, Long mobileNumber, String about,
			LocalDate dateOfBirth, String gender, String role, String password) {
		super();
		this.profileId = profileId;
		this.username = username;
		this.image = image;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.about = about;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.role = role;
		this.password = password;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return "UserProfile [profielId=" + profileId + ", username=" + username + ", image=" + image + ", email="
				+ email + ", mobileNumber=" + mobileNumber + ", about=" + about + ", dateOfBirth=" + dateOfBirth
				+ ", gender=" + gender + ", role=" + role + ", password=" + password + ", addresses=" + addresses + "]";
	}

}
