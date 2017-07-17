package com.easyrr.EasyResponse;

import java.net.URI;
import java.net.URISyntaxException;

public class EasyRequest {

	public EasyRequest(EasyContext context, RequestConfigurationFactory requestConfigurationFactory) {
		// TODO Auto-generated constructor stub
	}

	public RequestStream send(URI requestPath) {
		return new EasyRequestStream(requestPath);
	}
	
	public RequestStream send(String requestPath) throws URISyntaxException {
		return new EasyRequestStream(requestPath);
	}

	public ResponseStream onResponseFrom(URI requestPath) {
		
		return new EasyResponseStream(requestPath);
	}


}
