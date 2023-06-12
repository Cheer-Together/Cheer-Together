package com.ssafy.cheertogether.member.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.cheertogether.favorite.domain.FavoriteLeague;
import com.ssafy.cheertogether.favorite.domain.FavoriteTeam;
import com.ssafy.cheertogether.member.dto.MemberJoinRequest;
import com.ssafy.cheertogether.member.dto.MemberModifyRequest;
import com.ssafy.cheertogether.member.dto.Oauth2JoinRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Member implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String nickname;
	private String password;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("USER")
	private Role role;

	private String myInfo;

	@ColumnDefault("20")
	private Integer point;

	private LocalDateTime recentLogin;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<FavoriteLeague> favoriteLeagueList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<FavoriteTeam> favoriteTeamList = new ArrayList<>();

	@Builder
	public Member(String email, String nickname, String password, Role role, String myInfo, Integer point) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
		this.myInfo = myInfo;
		this.point = point;
	}

	public static Member from(MemberJoinRequest memberJoinRequest) {
		return Member.builder()
			.email(memberJoinRequest.getEmail())
			.nickname(memberJoinRequest.getNickname())
			.password(memberJoinRequest.getPassword())
			.role(memberJoinRequest.getRole())
			.myInfo(memberJoinRequest.getMyInfo())
			.point(20)
			.build();
	}

	public static Member from(Oauth2JoinRequest oauth2JoinRequest) {
		return Member.builder()
			.email(oauth2JoinRequest.getEmail())
			.nickname(oauth2JoinRequest.getNickname())
			.role(oauth2JoinRequest.getRole())
			.myInfo(oauth2JoinRequest.getMyInfo())
			.point(20)
			.build();
	}

	public static Member from(MemberModifyRequest modifyRequest) {
		return Member.builder().nickname(modifyRequest.getNickname()).myInfo(modifyRequest.getMyInfo()).build();
	}

	public boolean confirmPassword(String password) {
		return this.password.equals(password);
	}

	public void update(MemberModifyRequest memberModifyRequest) {
		nickname = memberModifyRequest.getNickname();
		myInfo = memberModifyRequest.getMyInfo();
	}

	public void setFavoriteLeagueList(List<FavoriteLeague> favoriteLeagueList) {
		this.favoriteLeagueList = favoriteLeagueList;
	}

	public void setFavoriteTeamList(List<FavoriteTeam> favoriteTeamList) {
		this.favoriteTeamList = favoriteTeamList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void updatePassword(String newPassword) {
		this.password = newPassword;
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

	public void setTempPassword(String tempPassword) {
		password = tempPassword;
	}

	public void plusPoint(int point) {
		this.point += point;
	}

	public void subtractPoint(int point) {
		this.point -= point;
		if (this.point < 0) {
			this.point = 0;
		}
	}

	public void checkAttendance() {
		ZoneId zoneId = ZoneId.of("Asia/Seoul");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		LocalDateTime now = zonedDateTime.toLocalDateTime();
		if (recentLogin == null || (now.getYear() != recentLogin.getYear() || now.getMonth() != recentLogin.getMonth()
			|| now.getDayOfMonth() != recentLogin.getDayOfMonth())) {
			this.point += 20;
			this.recentLogin = now;
		}
	}
}
