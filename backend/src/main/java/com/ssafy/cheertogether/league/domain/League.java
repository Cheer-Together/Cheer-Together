package com.ssafy.cheertogether.league.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.cheertogether.team.domain.Team;

import lombok.Getter;

@Entity
@Getter
public class League {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "league")
	@JsonManagedReference
	private List<Team> teamList = new ArrayList<>();

	private String name;
	private String logo;
	private int apiId;
}
