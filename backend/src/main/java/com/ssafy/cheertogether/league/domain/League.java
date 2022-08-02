package com.ssafy.cheertogether.league.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ssafy.cheertogether.team.domain.Team;

import lombok.Getter;

@Entity
@Getter
public class League {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "league")
	private List<Team> teamList;

	private String name;
	private String logo;
	private int apiId;
}
