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
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.easyrr.EasyResponse.request.RequestObserver;

public class EasyApplicationOnAnnotation implements EasyContext {

	// private final Thread mainThread;

	public EasyApplicationOnAnnotation() {
		super();
	}

	private static final String PATH_CLASSIFIER = "/";
	private List<EasyService> registredServices = new ArrayList<EasyService>();
	private List<ActionHolder> avaliableActions = new ArrayList<ActionHolder>();
	private static final Logger LOG = Logger.getLogger(EasyApplicationOnAnnotation.class.getName());
	private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void register(EasyService firstDemoService) {
		String servicePath = firstDemoService.getServicePath();
		if (servicePath != null && !servicePath.isEmpty()) {
			this.registredServices.add(firstDemoService);
			ActionHolder actionHolder = new ActionHolder();
			actionHolder.holdService(firstDemoService);
			avaliableActions.add(actionHolder);
			firstDemoService.updateServiceContext(this);

		}
	}

	public List<EasyService> getRegistredServices() {
		return this.registredServices;
	}

	public void call(URI path, RequestObserver request, Optional<Object[]> params) {

		int avaliableServices = 0;
		for (ActionHolder holder : avaliableActions) {
			EasyService service = holder.getService();
			if (service.getServicePath().equals(servicePathDispatcher(path))) {
				if (holder.canHandle(methodPathDispatcher(path))) {
					LOG.info("CAN HANDLE SERVICE: " + service.getServicePath());
					avaliableServices++;
					request.updateRequest(EasyStatus.ACCEPTED, path);
					try {
						executorService.execute(new Runnable() {

							@Override
							public void run() {

								try {
									long timeStart = System.currentTimeMillis();
									holder.callMethod(methodPathDispatcher(path), params.orElse(new Object[] {}));
									long timeEnd = System.currentTimeMillis();
									LOG.info("TIME OF METHOD: " + (timeEnd - timeStart));
									request.updateRequest(EasyStatus.DONE, path);
								} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
										| NullPointerException | NoSuchMethodException | SecurityException e) {
									request.updateRequest(EasyStatus.FAIL, path);
									e.printStackTrace();
								}

							}
						});
					} catch (RejectedExecutionException | NullPointerException e) {
						request.updateRequest(EasyStatus.FAIL, path);
					}
				} else
					LOG.info("CAN'T HANDLE SERVICE: " + service.getServicePath());
			}

		}
		if (avaliableServices == 0) {
			request.updateRequest(EasyStatus.REJECTED, path);
		}
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
		// avaliableCommands.clear();
	}
}
