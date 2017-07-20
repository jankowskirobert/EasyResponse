package com.easyrr.EasyResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.easyrr.EasyResponse.request.RequestObserver;

public class EasyApplicationOnAnnotation implements EasyContext {

	private final Thread mainThread;

	public EasyApplicationOnAnnotation() {
		super();
		this.mainThread = new Thread(new Runnable() {

			@Override
			public void run() {

			}
		});
	}

	private static final String PATH_CLASSIFIER = "/";
	private List<EasyService> registredServices = new ArrayList<EasyService>();
	private Map<EasyService, List<EasyRegistredAction>> avaliableCommands = new HashMap<EasyService, List<EasyRegistredAction>>();
	private static final Logger LOG = Logger.getLogger(EasyApplicationOnAnnotation.class.getName());
	private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void register(EasyService firstDemoService) {
		String servicePath = firstDemoService.getServicePath();
		if (servicePath != null && !servicePath.isEmpty()) {
			this.registredServices.add(firstDemoService);
			avaliableCommands.put(firstDemoService, scanAnnotation(firstDemoService));
			firstDemoService.updateServiceContext(this);

		}
	}

	private List<EasyRegistredAction> scanAnnotation(Object firstDemoService) {
		List<EasyRegistredAction> actions = new ArrayList<EasyRegistredAction>();
		Class<?> c = firstDemoService.getClass();
		Method[] methods = c.getMethods();
		for (Method method : methods) {
			Annotation[] a = method.getAnnotations();
			if (a.length == 0) {
				LOG.info("EMPYT ANNOTATIONS ON METHOD: " + method.getName());
			} else {
				LOG.info("ANNOTATIONS ON METHOD: " + method.getName());
			}
			for (Annotation annotation : a) {
				LOG.info("ANNOTATION " + annotation.annotationType() + " ON METHOD: " + method.getName());
				if (annotation.annotationType().isAssignableFrom(EasyRegistredAction.class)) {
					EasyRegistredAction action = (EasyRegistredAction) annotation;
					LOG.info("SCAN FOR ACTION " + action.path());
					actions.add(action);
				}
			}
		}
		if (actions.size() == 0) {
			LOG.info("EMPYT RESULT");
		}
		return actions;
	}

	public List<EasyService> getRegistredServices() {
		return this.registredServices;
	}

	// public void call(URI path, RequestObserver request) {
	//
	// int avaliableServices = 0;
	// for (EasyService easyService : registredServices) {
	// if (easyService.getServicePath().equals(servicePathDispatcher(path))) {
	// if (canHandlePath(easyService, path)) {
	// avaliableServices++;
	// request.updateRequest(EasyStatus.ACCEPTED, path);
	// callRequestedMethod(path, request, easyService);
	// } else {
	//
	// }
	//
	// // executorService.shutdown();
	// }
	//
	// }
	//
	// }

	public void call(URI path, RequestObserver request, Optional<Object[]> params) {

		int avaliableServices = 0;
		for (EasyService easyService : registredServices) {
			if (easyService.getServicePath().equals(servicePathDispatcher(path))) {
				if (canHandlePath(easyService, path)) {
					avaliableServices++;
					request.updateRequest(EasyStatus.ACCEPTED, path);
					callRequestedMethod(path, request, easyService, params);
				}
				
				// executorService.shutdown();
			}

		}
		if(avaliableServices == 0){
			request.updateRequest(EasyStatus.REJECTED, path);
		}
	}

	private void callRequestedMethod(URI path, RequestObserver request, EasyService easyService,
			Optional<Object[]> params) {
		Object o = easyService;
		Class<?> c = easyService.getClass();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			Annotation[] a = method.getAnnotations();
			if (a.length == 0) {
				LOG.info("EMPYT ANNOTATIONS ON METHOD: " + method.getName());
			} else {
				LOG.info("ANNOTATIONS ON METHOD: " + method.getName());
			}
			for (Annotation annotation : a) {
				LOG.info("ANNOTATION " + annotation.annotationType() + " ON METHOD: " + method.getName());
				if (annotation.annotationType().isAssignableFrom(EasyRegistredAction.class)) {
					EasyRegistredAction action = (EasyRegistredAction) annotation;
					if (action.path().equals(methodPathDispatcher(path)))
						try {
							LOG.info("INVOKE ON METHOD: " + method.getName());
							executorService.execute(new Runnable() {

								@Override
								public void run() {
									try {
										long timeStart = System.currentTimeMillis();
										Object ret = method.invoke(o, params.orElse(new Object[] {}));
										long timeEnd = System.currentTimeMillis();
										LOG.info("TIME OF METHOD: " + (timeEnd - timeStart));
										request.updateRequest(EasyStatus.DONE, path);
									} catch (IllegalAccessException | IllegalArgumentException
											| InvocationTargetException e) {
										request.updateRequest(EasyStatus.FAIL, path);
									}

								}
							});
							// executorService.

						} catch (IllegalArgumentException e) {
							LOG.severe("INVOKE FAIL ON METHOD: " + method.getName());
							request.updateRequest(EasyStatus.FAIL, path);
						}
					continue;
				}
			}
		}
	}

	private boolean canHandlePath(EasyService easyService, final URI path) {
		LOG.info("GIVEN PATH - CAN HANDLE?: " + path.getPath());
		if (avaliableCommands.containsKey(easyService)) {
			LOG.info("FOUND SERVICE - CAN HANDLE?: " + easyService.getServicePath());
			List<EasyRegistredAction> action = avaliableCommands.get(easyService);
			if (action.isEmpty()) {
				return false;
			} else {
				LOG.info("FOUND SERVICE! " + easyService.getServicePath() + " BUT CAN HANDLE: "
						+ Arrays.toString(action.toArray()));
				LOG.info("WILL LOOK FOR:" + methodPathDispatcher(path));
				return action.stream().filter(x -> x.path().equals(methodPathDispatcher(path))).peek(x -> {
					LOG.info("SERVICE THAT CAN HANDLE:" + easyService.getServicePath());
				}).findAny().isPresent();
			}
		} else
			return false;
	}

	private String servicePathDispatcher(URI path) {
		String[] paths = path.getPath().split(PATH_CLASSIFIER);
		return paths[0];
	}

	private String methodPathDispatcher(URI path) {
		String[] paths = path.getPath().split(PATH_CLASSIFIER);
		String string = "";
		for (int i = 1; i < paths.length; i++) {
			string += paths[i];
			if (i != paths.length - 1) {
				string += PATH_CLASSIFIER;
			}
		}
		return string;
	}

	@Override
	public void resetContext() {
		registredServices.clear();
		avaliableCommands.clear();
	}
}
