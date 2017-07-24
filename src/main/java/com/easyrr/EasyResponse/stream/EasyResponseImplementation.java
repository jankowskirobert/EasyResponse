package com.easyrr.EasyResponse.stream;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import com.easyrr.EasyResponse.EasyResponse;
import com.easyrr.EasyResponse.EasyStatus;

class EasyResponseImplementation implements EasyResponse, Observer {
	Map<EasyStatus, Integer> accepted = new HashMap<EasyStatus, Integer>();
	private String url;
	private static final Logger LOG = Logger.getLogger(EasyResponseImplementation.class.getName());
	private long time;
	private EasyStatus status;

	public EasyResponseImplementation(String url, EasyStatus status) {
		super();
		this.url = url;
		this.status = status;
	}

	public void increaseAccepted(EasyStatus status, long time) {
		this.time = time;
		if (accepted.containsKey(status)) {
			Integer tmp = accepted.get(status);
			tmp++;
		} else {
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

	@Override
	public long getExecutionTimeMillis() {
		
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof EasyStatus){
			this.status = (EasyStatus) arg;
		}
	}

	@Override
	public EasyStatus getStatus() {
		return this.status;
	}

}
