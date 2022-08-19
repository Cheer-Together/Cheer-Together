package com.ssafy.cheertogether.game.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Assist {
	@Column(name = "assist_name")
	private String name;
}
