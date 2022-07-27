package com.ssafy.cheertogether.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

	@Id
	@GeneratedValue
	Long id;
	String email;
	String nickname;
	String password;
	String profileImage;
	String role;
	String myInfo;
}
