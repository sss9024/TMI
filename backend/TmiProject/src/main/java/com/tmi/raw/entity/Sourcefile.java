package com.tmi.raw.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sourcefile {
	private String name;
	private List<Line> lineList;
	private Counter instruction;
	private Counter line;
	private Counter complexity;
	private Counter method;
	private Counter innerClass;
	private Counter branch;
}
