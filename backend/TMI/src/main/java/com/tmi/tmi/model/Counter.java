package com.tmi.tmi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Counter {
	private String type;
	private int missed;
	private int covered;
}
