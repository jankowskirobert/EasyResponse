package com.easyrr.EasyResponse;

import java.net.URI;

import com.easyrr.EasyResponse.request.EasyRequestException;

public class EasyResponseStream implements ResponseStream {

	private URI requestPath;

	public EasyResponseStream(URI requestPath) {
		this.requestPath = requestPath;
		// TODO Auto-generated constructor stub
	}

	public void thenDo(EasyAction easyAction) {
		// TODO Auto-generated method stub
		
	}

	public EasyResponse get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseStream validate() throws EasyRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseStream andDoWhenRespond(EasyAction demoEasyAction) {
		// TODO Auto-generated method stub
		return null;
	}

}
