package com.ssafy.cheertogether.game.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Time {
	private Integer elapsed;
}
