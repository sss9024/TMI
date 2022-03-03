package com.tmi.raw.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PackageInnerClass {
	private String name;
	private List<Method> methodList;
	private Counter instruction;
	private Counter line;
	private Counter complexity;
	private Counter method;
	private Counter innerClass;
}
