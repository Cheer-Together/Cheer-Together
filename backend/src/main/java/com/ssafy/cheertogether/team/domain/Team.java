package com.ssafy.cheertogether.team.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.cheertogether.league.domain.League;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "league_id")
	@JsonBackReference
	private League league;

	private String name;
	private String logo;
	private String code;
	private int apiId;
	private String hanName;
}
