package com.easyrr.EasyResponse;

import java.util.List;

public interface EasyContext {

	void register(EasyService firstDemoService);
	public List<EasyService> getRegistredServices();
}
