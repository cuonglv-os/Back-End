package com.twinkle.api.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author cuonglv
 *
 */
public class UserDto {
	@JsonProperty("username")
	@NotBlank
    private String userName;

    @JsonProperty("password")
    @NotBlank
    private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
