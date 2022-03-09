package com.springAngular.server.enumeration;

public enum Status {
//enum constants calling the enum constructors	
	SERVER_UP("SERVER_UP"),
	SERVER_DOWN("SERVER_DOWN");
	
	private final String status;
	
// private enum constructor
	private Status(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
	
}
