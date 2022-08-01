package com.ssafy.cheertogether.league.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class League {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String logo;
	private int apiId;
}
