package com.tmi.tmi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Method {
	private String name;
	private Counter instruction;
	private Counter line;
	private Counter complexity;
	private Counter method;
	
}
