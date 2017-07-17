package com.easyrr.EasyResponse;

import java.util.ArrayList;
import java.util.List;

public class EasyApplicationOnAnnotation implements EasyContext {

	private List<EasyService> registredServices = new ArrayList<EasyService>(); 
	
	public void register(EasyService firstDemoService) {
		String servicePath = firstDemoService.getServicePath();
		if(servicePath != null && !servicePath.isEmpty()){
			this.registredServices.add(firstDemoService);
		}
	}

	public List<EasyService> getRegistredServices() {
		return this.registredServices;
	}

}
