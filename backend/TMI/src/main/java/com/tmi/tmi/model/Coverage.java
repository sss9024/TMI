package com.tmi.tmi.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "coverage")
public class Coverage {
	@Id
	private String _id;
	
	private String buildTime;
	private String projectName;
	private String gitUrl;
	private List<Package> packageList;
	private Counter instruction;
	private Counter line;
	private Counter branch;
	private Counter complexity;
	private Counter method;
	private Counter innerClass;
}
