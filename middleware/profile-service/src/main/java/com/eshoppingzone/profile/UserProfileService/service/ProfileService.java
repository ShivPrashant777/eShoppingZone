package com.eshoppingzone.profile.UserProfileService.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;

public interface ProfileService {
	public UserProfile addNewProfile(UserProfile userProfile);

	public List<UserProfile> getAllProfiles();

	public UserProfile getByProfileId(int profileId);

	public void updateProfile(UserProfile userProfile);

	public void deleteProfile(int profileId);

	public UserProfile findByMobileNo(Long mobileNumber);

	public UserProfile getByUsername(String userName);

	public String generateJwtToken(Authentication authenticationObj);

	public void validateJwtToken(String token);

	public boolean validateUserRole(String token, String role);
}
