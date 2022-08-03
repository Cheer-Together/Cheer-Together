package com.ssafy.cheertogether.member.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;

@Entity
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
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();

	public Member(String email, String nickname, String password, String profileImage, String role, String myInfo) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.profileImage = profileImage;
		this.role = role;
		this.myInfo = myInfo;
	}

	public static Member from(MemberJoinRequest memberJoinRequest) {
		return new Member(memberJoinRequest.getEmail(), memberJoinRequest.getNickname(),
			memberJoinRequest.getPassword(), memberJoinRequest.getProfileImage(), memberJoinRequest.getRole(),
			memberJoinRequest.getMyInfo());
	}

	public boolean confirmPassword(String password) {
		return this.password.equals(password);
	}

<<<<<<< Updated upstream
=======
	public void update(MemberModifyRequest memberModifyRequest) {
		nickname = memberModifyRequest.getNickname();
		password = memberModifyRequest.getPassword();
		profileImage = memberModifyRequest.getProfileImage();
		myInfo = memberModifyRequest.getProfileImage();
	}

	public void setFavoriteLeagueList(List<FavoriteLeague> favoriteLeagueList) {
		this.favoriteLeagueList = favoriteLeagueList;
	}

>>>>>>> Stashed changes
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
