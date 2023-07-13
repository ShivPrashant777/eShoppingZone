package com.eshoppingzone.profile.UserProfileService.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.eshoppingzone.profile.UserProfileService.exception.ProfileNotFoundException;
import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;
import com.eshoppingzone.profile.UserProfileService.repository.ProfileRepository;
import com.eshoppingzone.profile.UserProfileService.util.JwtUtil;

@Service
public class ProfileServiceImpl implements ProfileService {
	private static Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	private static final String NO_PROFILE_FOUND_MESSAGE = "No Profile Found";

	@Autowired
	public ProfileRepository profileRepository;

	@Autowired
	public JwtUtil jwtUtil;
//
//	@Autowired
//	private SequenceGeneratorService sequenceGenerator;

	public ProfileServiceImpl() {
		super();
	}

	@Override
	public UserProfile addNewProfile(UserProfile userProfile) {
		if (userProfile.getMobileNumber().toString().length() != 10) {
			throw new RuntimeException("Invalid Mobile Number");
		}
		String username = userProfile.getUsername();
		UserProfile findByUsername = profileRepository.findByUsername(username);
		if (findByUsername != null) {
			log.error("Profile with username already exists");
			throw new BadCredentialsException("Profile with username already exists");
		}
//		int profileId = sequenceGenerator.generateSequence(UserProfile.SEQUENCE_NAME);
//		userProfile.setProfileId(profileId);
		log.info("Profile Saved Successfully");
		return profileRepository.save(userProfile);
	}

	@Override
	public List<UserProfile> getAllProfiles() {
		log.info("Retrieved All Profiles Successfully");
		return profileRepository.findAll();
	}

	@Override
	public UserProfile getByProfileId(int profileId) {
		UserProfile dbProfile = profileRepository.findById(profileId).orElse(null);
		if (dbProfile == null) {
			log.error(NO_PROFILE_FOUND_MESSAGE);
			throw new ProfileNotFoundException(NO_PROFILE_FOUND_MESSAGE);
		}
		log.info("Profile Retrieved Successfully");
		return dbProfile;
	}

	@Override
	public void updateProfile(UserProfile userProfile) {
		int profileId = userProfile.getProfileId();
		UserProfile dbProfile = getByProfileId(profileId);
		if (dbProfile == null) {
			log.error(NO_PROFILE_FOUND_MESSAGE);
			throw new ProfileNotFoundException(NO_PROFILE_FOUND_MESSAGE);
		}
		userProfile.setProfileId(profileId);
		userProfile.setUsername(dbProfile.getUsername());
		userProfile.setRole(dbProfile.getRole());
		userProfile.setPassword(dbProfile.getPassword());
		log.info("Profile Updated Successfully");
		profileRepository.save(userProfile);
	}

	@Override
	public void deleteProfile(int profileId) {
		UserProfile dbProfile = getByProfileId(profileId);
		if (dbProfile == null) {
			log.error(NO_PROFILE_FOUND_MESSAGE);
			throw new ProfileNotFoundException(NO_PROFILE_FOUND_MESSAGE);
		}
		log.info("Profile Deleted Successfully");
		profileRepository.deleteById(profileId);
	}

	@Override
	public UserProfile findByMobileNo(Long mobileNumber) {
		List<UserProfile> findAllByMobileNumber = profileRepository.findAllByMobileNumber(mobileNumber);
		if (findAllByMobileNumber.isEmpty()) {
			log.error(NO_PROFILE_FOUND_MESSAGE);
			throw new ProfileNotFoundException(NO_PROFILE_FOUND_MESSAGE);
		}
		log.info("Profile Retrieved Successfully");
		return findAllByMobileNumber.get(0);
	}

	@Override
	public UserProfile getByUsername(String username) {
		UserProfile findByUsername = profileRepository.findByUsername(username);
		if (findByUsername == null) {
			log.error(NO_PROFILE_FOUND_MESSAGE);
			throw new ProfileNotFoundException(NO_PROFILE_FOUND_MESSAGE);
		}
		log.info("Profile Retrieved Successfully");
		return findByUsername;
	}

	@Override
	public String generateJwtToken(Authentication authenticationObj) {
		return jwtUtil.generateToken(authenticationObj);
	}

	@Override
	public void validateJwtToken(String token) {
		jwtUtil.validateToken(token);
	}

	@Override
	public boolean validateUserRole(String token, String role) {
		return jwtUtil.validateUserRole(token, role);
	}

}
