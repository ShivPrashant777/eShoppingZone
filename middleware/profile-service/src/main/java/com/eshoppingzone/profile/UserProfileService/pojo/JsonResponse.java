package com.eshoppingzone.profile.UserProfileService.pojo;

import java.util.List;

public class JsonResponse {
	private String token;
	private String username;
	private int profileId;
	private List<String> roles;

	public JsonResponse() {
		super();
	}

	public JsonResponse(String token, String username, int profileId, List<String> roles) {
		super();
		this.token = token;
		this.username = username;
		this.profileId = profileId;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
