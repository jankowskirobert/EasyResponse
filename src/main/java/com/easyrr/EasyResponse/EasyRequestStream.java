package com.easyrr.EasyResponse;

import java.net.URI;

public class EasyRequestStream implements RequestStream {

	private URI requestPath;

	public EasyRequestStream(URI requestPath) {
		this.requestPath = requestPath;
	}

	public RequestStream andDoWhenRespond(DemoEasyAction demoEasyAction) {
		return this;
	}

	public EasyResponse get() {
		// TODO Auto-generated method stub
		return null;
	}

}
