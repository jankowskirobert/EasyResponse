package com.easyrr.EasyResponse;

import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.easyrr.EasyResponse.application.EasyApplicationOnAnnotation;
import com.easyrr.EasyResponse.stream.EasyRequest;
import com.easyrr.EasyResponse.stream.EasyRequestException;
import com.easyrr.EasyResponse.stream.RequestConfigurationFactory;

public class EasyStreamTest {

	EasyContext context = new EasyApplicationOnAnnotation();
	
	EasyService demoService = new EasyService() {
		private String path;
		private EasyContext context;
		private int val = 0;

		@EasyRegistredAction(request = "method_no_args")
		public void doSomething() {
			val++;
		}

		@Override
		public void updateServiceContext(EasyContext context) {
			this.context = context;
		}

		@Override
		public String getServicePath() {
			return path;
		}

		@Override
		public void configurePath(String path) {
			this.path = path;
		}
	};

	@Before
	public void setUp() {
		demoService.configurePath("demo");
		context.register(demoService);
	}

	@Test
	public void ops() throws EasyRequestException, URISyntaxException {
		// context.register(demoService);
		// EasyRequest request = new EasyRequest(context);
		// EasyResponse response =
		// request.to("demo/cant_handle").sendSync().validate().get();
		// Assert.assertTrue(response.getStatus().equals(EasyStatus.REJECTED));
		// try {
		//
		// } finally {
		//
		// }

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		CompletionService<Event> completionService = new ExecutorCompletionService<Event>(executor);
		executor.scheduleAtFixedRate(() -> {
			System.out.println(".");
//			System.out.flush();
		}, 1, 1, TimeUnit.SECONDS);
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			completionService.submit(new TaskDemo(String.valueOf(r.nextInt(300)), "a"));
		}
		try {
			for (int t = 0, n = 10; t < n; t++) {
				Future<Event> f = completionService.take();
				System.out.println(f.get());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			Thread.currentThread().interrupt();
		} finally {
			if (executor != null) {
				executor.shutdownNow();
			}
		}
	}
}
