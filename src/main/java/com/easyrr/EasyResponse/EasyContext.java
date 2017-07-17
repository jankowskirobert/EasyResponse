package com.easyrr.EasyResponse;

import java.net.URI;
import java.util.List;

import com.easyrr.EasyResponse.request.RequestObserver;

public interface EasyContext {

	public void register(EasyService firstDemoService);
	public List<EasyService> getRegistredServices();
	public EasyStatus call(URI path, RequestObserver request);
}
