package com.easyrr.EasyResponse.request;

import java.net.URI;
import java.net.URISyntaxException;

import com.easyrr.EasyResponse.EasyContext;
import com.easyrr.EasyResponse.EasyResponseStream;
import com.easyrr.EasyResponse.ResponseStream;

public class EasyRequest {

	private EasyContext context;

	public EasyRequest(EasyContext context, RequestConfigurationFactory requestConfigurationFactory) {
		this.context = context;
	}

	public RequestStream to(URI requestPath) {
		return new EasyStream(context, requestPath);
	}
	
	public ResponseStream onResponseFrom(URI requestPath) {		
		return new EasyResponseStream(requestPath);
	}

	public RequestStream to(String requestPath) throws URISyntaxException {
		return new EasyStream(context, requestPath);
	}


}
