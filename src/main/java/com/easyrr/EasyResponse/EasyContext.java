package com.easyrr.EasyResponse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface EasyContext {

	public void register(EasyService firstDemoService);
	public List<EasyService> getRegistredServices();
//	public void call(URI path, RequestObserver request);
	public void call(URI path, RequestObserver request, Optional<Object[]> params);
	public void call(EasyMessage message);
	public void resetContext();
}
