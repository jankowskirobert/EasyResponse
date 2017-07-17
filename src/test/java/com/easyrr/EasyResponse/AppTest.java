package com.easyrr.EasyResponse;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void registredServiceIsPresentInContext_addOneService() {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("demo");
		context.register(firstDemoService);
		Assert.assertTrue(context.getRegistredServices().contains(firstDemoService));
	}

	@Test
	public void registredServiceIsNotPresentInContext_addOneServiceWithWrongPath() {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();

		firstDemoService.configurePath("");
		context.register(firstDemoService);
		Assert.assertFalse(context.getRegistredServices().contains(firstDemoService));

		firstDemoService.configurePath(null);
		context.register(firstDemoService);
		Assert.assertFalse(context.getRegistredServices().contains(firstDemoService));
	}

	@Test
	public void testApp() throws URISyntaxException {
		EasyContext context = new EasyApplicationOnAnnotation();
		long responseTimeout = 1000; // ms

		// insulation
		EasyService firstDemoService = new DemoEasyService();
		context.register(firstDemoService);
		// insulation

		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		URI requestPath = new URI("demo/demo_path_nope");
		EasyResponse response = request.send(requestPath).andDoWhenRespond(new DemoEasyAction()).get();
		request.onResponseFrom(requestPath).thenDo(new DemoEasyAction());
		ResponseWithTimeout responseWithTimeOut = new ResponseWithTimeout(responseTimeout);
	}
}
