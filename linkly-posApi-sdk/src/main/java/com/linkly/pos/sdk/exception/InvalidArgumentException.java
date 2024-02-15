package com.linkly.pos.sdk.exception;

public class InvalidArgumentException extends RuntimeException {

	private static final long serialVersionUID = -2607176576419765232L;

	public InvalidArgumentException(String message) {
		super(message);
	}
}