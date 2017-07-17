package com.easyrr.EasyResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class EasyApplicationOnAnnotation implements EasyContext {

	private static final String PATH_CLASSIFIER = "/";
	private List<EasyService> registredServices = new ArrayList<EasyService>();

	public void register(EasyService firstDemoService) {
		String servicePath = firstDemoService.getServicePath();
		if (servicePath != null && !servicePath.isEmpty()) {
			this.registredServices.add(firstDemoService);
		}
	}

	public List<EasyService> getRegistredServices() {
		return this.registredServices;
	}

	public EasyStatus call(URI path) {		
		int avaliableServices = 0;
		for (EasyService easyService : registredServices) {
			if (easyService.getServicePath().equals(pathDispatcher(path))) {
				if (canHandlePath(easyService)) {
					avaliableServices++;
				}
			}
		}
		return (avaliableServices == 0) ? EasyStatus.REJECTED : EasyStatus.ACCEPTED;
	}

	private boolean canHandlePath(EasyService easyService) {
		// TODO Auto-generated method stub
		return false;
	}

	private String pathDispatcher(URI path) {
		String[] paths = path.getPath().split(PATH_CLASSIFIER);
		return paths[0];
	}
}
