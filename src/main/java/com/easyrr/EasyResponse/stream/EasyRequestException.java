package com.easyrr.EasyResponse.stream;

public class EasyRequestException extends Exception {

	public EasyRequestException() {
		super();
	}

	public EasyRequestException(String message) {
		super("Take it easy but: " + message);
	}

}
