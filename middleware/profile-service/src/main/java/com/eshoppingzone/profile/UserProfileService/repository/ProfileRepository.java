package com.eshoppingzone.profile.UserProfileService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshoppingzone.profile.UserProfileService.pojo.UserProfile;

public interface ProfileRepository extends JpaRepository<UserProfile, Integer> {
	public List<UserProfile> findAllByMobileNumber(Long mobileNumber);

	public UserProfile findByUsername(String username);
}
