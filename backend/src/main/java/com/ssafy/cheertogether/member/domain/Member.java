package com.ssafy.cheertogether.member.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

	@Id
	@GeneratedValue
	private Long id;
	private String email;
	private String nickname;
	private String password;
	private String profileImage;
	private String role;
	private String myInfo;

	@Builder
	public Member(String email, String nickname, String password, String profileImage, String role, String myInfo) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.profileImage = profileImage;
		this.role = role;
		this.myInfo = myInfo;
	}

	public static Member from(MemberJoinRequest memberJoinRequest) {
		return Member.builder()
			.email(memberJoinRequest.getEmail())
			.nickname(memberJoinRequest.getNickname())
			.password(memberJoinRequest.getPassword())
			.profileImage(memberJoinRequest.getProfileImage())
			.role(memberJoinRequest.getRole())
			.myInfo(memberJoinRequest.getMyInfo())
			.build();
	}

	public static Member from(MemberModifyRequest modifyRequest) {
		return Member.builder()
			.nickname(modifyRequest.getNickname())
			.password(modifyRequest.getPassword())
			.profileImage(modifyRequest.getProfileImage())
			.myInfo(modifyRequest.getMyInfo())
			.build();
	}

	public boolean confirmPassword(String password) {
		return this.password.equals(password);
	}

	public void update(MemberModifyRequest memberModifyRequest) {
		nickname = memberModifyRequest.getNickname();
		password = memberModifyRequest.getPassword();
		profileImage = memberModifyRequest.getProfileImage();
		myInfo = memberModifyRequest.getProfileImage();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
