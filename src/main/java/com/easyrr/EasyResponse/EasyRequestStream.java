package com.easyrr.EasyResponse;

import java.net.URI;
import java.net.URISyntaxException;

public class EasyRequestStream implements RequestStream {

	private URI requestPath;

	public EasyRequestStream(URI requestPath) {
		this.requestPath = requestPath;
	}

	public EasyRequestStream(String requestPath) throws URISyntaxException {
		this.requestPath = new URI(requestPath);
	}

	public RequestStream andDoWhenRespond(DemoEasyAction demoEasyAction) {
		return this;
	}

	public EasyResponse get() {
		// TODO Auto-generated method stub
		return null;
	}

}
