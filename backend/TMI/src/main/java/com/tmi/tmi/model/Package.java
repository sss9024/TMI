package com.tmi.tmi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Package {
	private String name;
	private List<PackageInnerClass> classList;
	private List<Sourcefile> sourceFileList;
	private Counter instruction;
	private Counter line;
	private Counter complexity;
	private Counter method;
	private Counter innerClass;
}