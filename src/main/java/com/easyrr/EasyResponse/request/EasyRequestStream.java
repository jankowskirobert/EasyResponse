package com.easyrr.EasyResponse.request;

import java.net.URI;
import java.net.URISyntaxException;

import com.easyrr.EasyResponse.DemoEasyAction;
import com.easyrr.EasyResponse.EasyContext;
import com.easyrr.EasyResponse.EasyResponse;
import com.easyrr.EasyResponse.EasyStatus;
import com.easyrr.EasyResponse.ResponseStream;

public class EasyRequestStream implements RequestStream {

	private EasyContext context;
	private URI requestPath;
	private EasyStatus status;

	public EasyRequestStream(EasyContext context, String requestPath) throws URISyntaxException {
		this(context, new URI(requestPath));
	}

	public EasyRequestStream(EasyContext context, URI requestPath) {
		this.context = context;
		this.requestPath = requestPath;
	}

	public ResponseStream andDoWhenRespond(DemoEasyAction demoEasyAction) {
		return null;
	}

	public EasyResponse get() {
		// TODO Auto-generated method stub
		return null;
	}

	public RequestStream send() {
		context.call(requestPath);
		return this;
	}

	public RequestStream validate() throws EasyRequestException {
		if (status.equals(EasyStatus.REJECTED)) {
			throw new EasyRequestException("Request Rejected");
		} else
			return this;
	}

}
