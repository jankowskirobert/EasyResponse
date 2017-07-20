package com.easyrr.EasyResponse.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import com.easyrr.EasyResponse.DemoEasyAction;
import com.easyrr.EasyResponse.EasyAction;
import com.easyrr.EasyResponse.EasyContext;
import com.easyrr.EasyResponse.EasyResponse;
import com.easyrr.EasyResponse.EasyStatus;
import com.easyrr.EasyResponse.ResponseStream;

class EasyStream implements RequestStream, RequestObserver, ResponseStream {

	private EasyContext context;
	private URI requestPath;
	private EasyStatus status;
	private EasyAction demoEasyAction;
	private final EasyResponseImplementation response;

	private static final Logger LOG = Logger.getLogger(EasyStream.class.getName());

	EasyStream(EasyContext context, String requestPath) throws URISyntaxException {
		this(context, new URI(requestPath));
	}

	EasyStream(EasyContext context, URI requestPath) {
		this.context = context;
		this.requestPath = requestPath;
		this.response = new EasyResponseImplementation(requestPath.getPath());
		// this.response.
	}

	public ResponseStream andDoWhenRespond(EasyAction demoEasyAction) {
		this.demoEasyAction = demoEasyAction;
		return this;
	}

	public EasyResponse get() {
		return response;
	}

	public ResponseStream send() {
		status = context.call(requestPath, this);
		return this;
	}

	public ResponseStream validate() throws EasyRequestException {
		if (status.equals(EasyStatus.REJECTED)) {
			throw new EasyRequestException("Request Rejected");
		} else
			return this;
	}

	public void updateRequest(EasyStatus status, URI path) {
		this.status = status;
		LOG.info("UPDATE REQUEST: " + status);
		if (path.getPath().equals(requestPath.getPath())) {
			LOG.info("UPDATE REQUEST: " + status);
			switch (status) {
			case ACCEPTED:
				LOG.info("UPDATE REQUEST: " + status);
				response.increaseAccepted(status);
				break;
			case FAIL:
				break;
			case DONE:
				if (demoEasyAction != null) {
					demoEasyAction.execute();
				}
				break;
			case REJECTED:
				break;
			case NEW:
				break;
			default:
				LOG.info("UPDATE REQUEST: " + status);
				break;
			}
		}

	}

}
