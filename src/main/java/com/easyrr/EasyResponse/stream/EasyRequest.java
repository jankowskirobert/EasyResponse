package com.easyrr.EasyResponse.stream;

import java.net.URI;
import java.net.URISyntaxException;

import com.easyrr.EasyResponse.EasyContext;
import com.easyrr.EasyResponse.EasyResponseStream;

public class EasyRequest {

	private EasyContext context;

	public EasyRequest(EasyContext context, RequestConfigurationFactory requestConfigurationFactory) {
		this.context = context;
	}
	public EasyRequest(EasyContext context) {
		this.context = context;
	}
	public RequestStream to(URI requestPath) {
		return new EasyStream(context, requestPath);
	}

	public RequestStream to(String requestPath) throws URISyntaxException {
		return new EasyStream(context, requestPath);
	}


}
