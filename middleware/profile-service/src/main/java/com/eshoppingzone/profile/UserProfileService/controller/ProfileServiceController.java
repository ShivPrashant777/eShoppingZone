package com.eshoppingzone.profile.UserProfileService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingzone.profile.UserProfileService.pojo.Address;
import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;
import com.eshoppingzone.profile.UserProfileService.service.ProfileService;

@RestController
@RequestMapping("/profile-service/profiles")
@CrossOrigin(origins = "*")
public class ProfileServiceController {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/id/{profileId}")
	public UserProfile getByProfileId(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
			@PathVariable int profileId) {
		return profileService.getByProfileId(profileId);

	}

	@GetMapping("/getAll")
	public List<UserProfile> getAllProfiles() {
		return profileService.getAllProfiles();
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateProfile(@RequestBody UserProfile userProfile) {
		profileService.updateProfile(userProfile);
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body(userProfile);
		return responseEntity;
	}

	@DeleteMapping("/delete/{profileId}")
	public ResponseEntity<Object> deleteProfile(@PathVariable int profileId) {
		profileService.deleteProfile(profileId);
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK)
				.body("Profile deleted successfully");
		return responseEntity;
	}

	@GetMapping("/mobile/{mobile}")
	public UserProfile findByMobileNo(@PathVariable Long mobile) {
		return profileService.findByMobileNo(mobile);
	}

	@GetMapping("/username/{username}")
	public UserProfile getByUserName(@PathVariable String username) {
		return profileService.getByUsername(username);
	}

	@GetMapping("/address/{profileId}")
	public List<Address> getUserAddressesByProfileId(@PathVariable int profileId) {
		return profileService.getByProfileId(profileId).getAddresses();
	}

}
