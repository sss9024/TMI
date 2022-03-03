package com.tmi.controller.app;

public class AppDuplicatedException extends RuntimeException {
	public AppDuplicatedException(String title, String gitUrl) {
		super("App Duplicated Title : " + title + ", gitUrl :" + gitUrl);
	}
}
