package com.eshoppingzone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import com.eshoppingzone.profile.UserProfileService.exception.ProfileNotFoundException;
import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;
import com.eshoppingzone.profile.UserProfileService.repository.ProfileRepository;
import com.eshoppingzone.profile.UserProfileService.service.ProfileServiceImpl;
import com.eshoppingzone.profile.UserProfileService.util.JwtUtil;

@SpringBootTest
public class ProfileServiceImplTest {
	private static final String NO_PROFILE_FOUND_MESSAGE = "No Profile Found";

	@Mock
	private ProfileRepository profileRepository;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private ProfileServiceImpl profileService;

	@Test
	void testAddNewProfileMobileNumberIsValidAndUsernameDoesNotExist() {
		UserProfile userProfile = new UserProfile();
		userProfile.setUsername("testUser");
		userProfile.setMobileNumber((long) 1234567890);

		when(profileRepository.findByUsername(userProfile.getUsername())).thenReturn(null);
		when(profileRepository.save(userProfile)).thenReturn(userProfile);

		UserProfile savedProfile = profileService.addNewProfile(userProfile);

		assertNotNull(savedProfile);
		assertEquals(userProfile, savedProfile);
		verify(profileRepository, times(1)).save(userProfile);
	}

	@Test
	void testAddNewProfileMobileNumberIsInvalid() {
		UserProfile userProfile = new UserProfile();
		userProfile.setUsername("testUser");
		userProfile.setMobileNumber((long) 1234567);

		assertThrows(RuntimeException.class, () -> profileService.addNewProfile(userProfile));
		verify(profileRepository, never()).save(userProfile);
	}

	@Test
	void testAddNewProfileUsernameAlreadyExists() {
		UserProfile userProfile = new UserProfile();
		userProfile.setUsername("testUser");
		userProfile.setMobileNumber((long) 1234567890);

		when(profileRepository.findByUsername(userProfile.getUsername())).thenReturn(userProfile);

		assertThrows(BadCredentialsException.class, () -> profileService.addNewProfile(userProfile));
		verify(profileRepository, never()).save(userProfile);
	}

	@Test
	void testGetAllProfiles() {
		List<UserProfile> mockProfiles = new ArrayList<>();
		mockProfiles.add(new UserProfile());
		mockProfiles.add(new UserProfile());

		when(profileRepository.findAll()).thenReturn(mockProfiles);

		List<UserProfile> allProfiles = profileService.getAllProfiles();

		assertNotNull(allProfiles);
		assertEquals(mockProfiles, allProfiles);
	}

	@Test
	void testGetByProfileIdWhenProfileExists() {
		int profileId = 1;
		UserProfile mockProfile = new UserProfile();

		when(profileRepository.findById(profileId)).thenReturn(Optional.of(mockProfile));

		UserProfile profile = profileService.getByProfileId(profileId);

		assertNotNull(profile);
		assertEquals(mockProfile, profile);
	}

	@Test
	void testGetByProfileIdWhenProfileDoesNotExist() {
		int nonExistingProfileId = 999;

		when(profileRepository.findById(nonExistingProfileId)).thenReturn(Optional.empty());

		assertThrows(ProfileNotFoundException.class, () -> profileService.getByProfileId(nonExistingProfileId));
	}
}
