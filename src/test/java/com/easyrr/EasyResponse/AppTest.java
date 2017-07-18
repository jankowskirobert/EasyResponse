package com.easyrr.EasyResponse;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.easyrr.EasyResponse.request.EasyRequest;
import com.easyrr.EasyResponse.request.EasyRequestException;
import com.easyrr.EasyResponse.request.RequestConfigurationFactory;

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

	@Test(expected=EasyRequestException.class)
	public void requestThrowsExceptionIfThereIsAnyServiceHandleIt() throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		// insulation
		EasyService firstDemoService = new DemoEasyService();		
		firstDemoService.configurePath("good");
		context.register(firstDemoService);
		// insulation
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response = request.to("bad/nothing").send().validate().get();
	}
	
	@Test(expected=EasyRequestException.class)
	public void requestThrowsExceptionIfThereIsService_butCantHandleIt() throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		// insulation
		EasyService firstDemoService = new DemoEasyService();		
		firstDemoService.configurePath("bad");
		context.register(firstDemoService);
		// insulation
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response = request.to("bad/forSureServiceCantHandleIt").send().validate().get();
	}
	
	@Test
	public void serviceRequestToValidation_twoGoodRequests() throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("demo1");
		EasyService secondDemoService = new DemoEasyService();
		secondDemoService.configurePath("demo2");
		context.register(firstDemoService);
		context.register(secondDemoService);
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());		
		EasyResponse response1 = request.to("demo1/demo_path_nope").send().validate().get();
		EasyResponse response2 = request.to("demo1/demo_path_yep/ops").send().validate().get();		
	}
	
	@Ignore
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
		EasyResponse response = request.to(requestPath).send().andDoWhenRespond(new DemoEasyAction()).get();
		request.onResponseFrom(requestPath).thenDo(new DemoEasyAction());
		ResponseWithTimeout responseWithTimeOut = new ResponseWithTimeout(responseTimeout);
	}
}
