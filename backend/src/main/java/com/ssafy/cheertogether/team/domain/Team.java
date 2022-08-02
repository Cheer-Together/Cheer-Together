package com.ssafy.cheertogether.team.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.cheertogether.league.domain.League;

import lombok.Getter;

@Getter
@Entity
public class Team {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "api_id")
	private League league;

	private String name;
	private String logo;
	private String code;
	private int apiId;
}
