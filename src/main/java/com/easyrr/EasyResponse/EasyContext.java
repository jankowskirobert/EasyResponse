package com.easyrr.EasyResponse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.easyrr.EasyResponse.request.RequestObserver;

public interface EasyContext {

	public void register(EasyService firstDemoService);
	public List<EasyService> getRegistredServices();
//	public void call(URI path, RequestObserver request);
	public void call(URI path, RequestObserver request, Optional<Object[]> params);
	public void resetContext();
}
