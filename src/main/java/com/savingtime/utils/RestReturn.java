package com.savingtime.utils;

import java.util.List;

import javax.ws.rs.core.Response.Status;

public class RestReturn {
	
	private Status status;
	private List<?> object;
	private String message;
	private int value;


	public RestReturn(Status status, List<?> object, String message) {
		this.status = status;
		this.object = object;
		this.setMessage(message);
	}
	
	public RestReturn(Status status, int value, String message) {
		this.status = status;
		this.setValue(value);
		this.setMessage(message);
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<?> getObject() {
		return object;
	}

	public void setObject(List<?> object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
