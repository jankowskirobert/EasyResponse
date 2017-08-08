package com.easyrr.EasyResponse.application;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.easyrr.EasyResponse.EasyRegistredAction;
import com.easyrr.EasyResponse.EasyService;

class ActionHolder {
	private EasyService service;

	private static final Logger LOG = Logger.getLogger(ActionHolder.class.getName());

	public EasyService getService() {
		return service;
	}

	private Map<String, Method> avaliableServices = new TreeMap<String, Method>(new Comparator<String>() {

		@Override
		public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
	});

	void holdService(EasyService service) {
		this.service = service;

		Class<?> c = ((Object) service).getClass();
		Method[] methods = c.getMethods();
		for (Method method : methods) {
			method.setAccessible(true);
			Annotation[] a = method.getAnnotations();
			for (Annotation annotation : a) {
				if (annotation.annotationType().isAssignableFrom(EasyRegistredAction.class)) {
					EasyRegistredAction action = (EasyRegistredAction) annotation;
					LOG.info("REGISTER SERVICE: " + service.getServicePath() + " PATH: " + action.request() + " METHOD: "
							+ method.getName());
					avaliableServices.put(action.request(), method);
				}
			}
		}
	}

	void callMethod(String path, Object[] obj) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NullPointerException, NoSuchMethodException, SecurityException {
		//
		// for (Map.Entry<String, Method> object : avaliableServices.entrySet())
		// {
		// LOG.info(object.getKey() + " " + object.getValue());
		// }
		synchronized (this) {
			Method method = avaliableServices.get(path);
			if (method != null) {
				// method.invoke(service, obj);
				LOG.info("METHOD TO INVOKE: " + method.getName());
				Object returnedValue = method.invoke(service, obj);
			} else
				throw new NullPointerException("TRIED TO FIND AND INVOKE: " + path);
		}

	}

	boolean canHandle(String path) {
		LOG.info("HANDLE SERVICE: " + service.getServicePath() + " PATH: " + path);
		return avaliableServices.containsKey(path);
	}

}
