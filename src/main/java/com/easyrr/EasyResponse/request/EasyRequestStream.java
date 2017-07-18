package com.easyrr.EasyResponse.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

import com.easyrr.EasyResponse.DemoEasyAction;
import com.easyrr.EasyResponse.EasyAction;
import com.easyrr.EasyResponse.EasyContext;
import com.easyrr.EasyResponse.EasyResponse;
import com.easyrr.EasyResponse.EasyStatus;
import com.easyrr.EasyResponse.ResponseStream;

public class EasyRequestStream implements AcceptedRequestStream, RequestObserver {

	private EasyContext context;
	private URI requestPath;
	private EasyStatus status;
	private EasyAction demoEasyAction;

	public EasyRequestStream(EasyContext context, String requestPath) throws URISyntaxException {
		this(context, new URI(requestPath));
	}

	public EasyRequestStream(EasyContext context, URI requestPath) {
		this.context = context;
		this.requestPath = requestPath;
	}

	public ResponseStream andDoWhenRespond(EasyAction demoEasyAction) {
		this.demoEasyAction = demoEasyAction;
		return null;
	}

	public EasyResponse get() {
		// TODO Auto-generated method stub
		return null;
	}

	public AcceptedRequestStream send() {
		status = context.call(requestPath, this);
		System.out.println(status);
		return this;
	}

	public AcceptedRequestStream validate() throws EasyRequestException {
		if (status.equals(EasyStatus.REJECTED)) {
			throw new EasyRequestException("Request Rejected");
		} else
			return this;
	}

	public void updateRequestStatus(EasyStatus status) {
		this.status = status;
		System.out.println(status);
	}

}
