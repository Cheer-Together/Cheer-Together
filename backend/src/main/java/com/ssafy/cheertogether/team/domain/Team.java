package com.ssafy.cheertogether.team.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.firebase.database.annotations.NotNull;
import com.ssafy.cheertogether.league.domain.League;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Team implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "league_id")
	@JsonBackReference
	private League league;
	@NotNull
	private String name;
	@NotNull
	private String logo;
	@NotNull
	private String code;
	@Column(name = "api_id", unique = true)
	@NotNull
	private Long apiId;
	@NotNull
	private String hanName;
}
