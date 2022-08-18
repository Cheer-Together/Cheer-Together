package com.ssafy.cheertogether.game.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Player {
	@Column(name = "player_name")
	private String name;
}
