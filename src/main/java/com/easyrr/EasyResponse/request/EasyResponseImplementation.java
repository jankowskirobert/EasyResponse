package com.easyrr.EasyResponse.request;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.easyrr.EasyResponse.EasyResponse;
import com.easyrr.EasyResponse.EasyStatus;

class EasyResponseImplementation implements EasyResponse {
	Map<EasyStatus, Integer> accepted = new HashMap<EasyStatus, Integer>();
	private String url;
	private static final Logger LOG = Logger.getLogger(EasyResponseImplementation.class.getName());

	public EasyResponseImplementation(String url) {
		super();
		this.url = url;
	}

	public void increaseAccepted(EasyStatus status) {
		if (accepted.containsKey(status)) {
			LOG.info("INCREASE ACCEPTED");
			Integer tmp = accepted.get(status);
			tmp++;
		} else {
			LOG.info("PUT INCREASE ACCEPTED");
			accepted.putIfAbsent(status, 1);
		}
	}

	@Override
	public int getAccepted() {
		if (accepted.containsKey(EasyStatus.ACCEPTED)) {
			Integer tmp = accepted.get(EasyStatus.ACCEPTED);
			return tmp.intValue();
		} else
			return -1;
	}

}
