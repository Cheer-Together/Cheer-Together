package com.ssafy.cheertogether.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
	USER("user"),
	ADMIN("admin");

	private final String text;

	Role(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@JsonCreator
	public static Role fromText(String text) {
		for (Role role : Role.values()) {
			if(role.name().equals(text)) {
				return role;
			}
		}
		return null;
	}
}
