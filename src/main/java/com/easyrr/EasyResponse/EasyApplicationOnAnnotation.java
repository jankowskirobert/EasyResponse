package com.easyrr.EasyResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.easyrr.EasyResponse.request.RequestObserver;

public class EasyApplicationOnAnnotation implements EasyContext {

	private static final String PATH_CLASSIFIER = "/";
	private List<EasyService> registredServices = new ArrayList<EasyService>();
	private Map<EasyService, List<EasyRegistredAction>> avaliableCommands = new HashMap<EasyService, List<EasyRegistredAction>>();
	
	public void register(EasyService firstDemoService) {
		String servicePath = firstDemoService.getServicePath();
		if (servicePath != null && !servicePath.isEmpty()) {
			this.registredServices.add(firstDemoService);								
			avaliableCommands.put(firstDemoService, scanAnnotation(firstDemoService));
			firstDemoService.updateServiceContext(this);
			
		}
	}

	private List<EasyRegistredAction> scanAnnotation(EasyService firstDemoService) {
		List<EasyRegistredAction> actions = new ArrayList<EasyRegistredAction>();
		Class c = firstDemoService.getClass();
		Method[] methods = c.getMethods();
		for (Method method : methods) {
			Annotation[] a = method.getAnnotations();
			for (Annotation annotation : a) {
				if(annotation.equals(EasyRegistredAction.class)){
					EasyRegistredAction action = (EasyRegistredAction) annotation;
					actions.add(action);
				}
			}
		}
		return actions;
	}

	public List<EasyService> getRegistredServices() {
		return this.registredServices;
	}

	public EasyStatus call(URI path, RequestObserver request) {		
		int avaliableServices = 0;
		for (EasyService easyService : registredServices) {
			if (easyService.getServicePath().equals(pathDispatcher(path))) {
				if (canHandlePath(easyService, path)) {
					avaliableServices++;
					System.out.println("yeee");
				}
			}
		}
		return (avaliableServices == 0) ? EasyStatus.REJECTED : EasyStatus.ACCEPTED;
	}

	private boolean canHandlePath(EasyService easyService,URI path) {		
		return avaliableCommands.containsKey(easyService);
	}

	private String pathDispatcher(URI path) {
		String[] paths = path.getPath().split(PATH_CLASSIFIER);
		return paths[0];
	}
}
