package com.easyrr.EasyResponse;

import java.lang.annotation.Repeatable;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.easyrr.EasyResponse.application.EasyApplicationOnAnnotation;
import com.easyrr.EasyResponse.stream.EasyRequest;
import com.easyrr.EasyResponse.stream.EasyRequestException;
import com.easyrr.EasyResponse.stream.RequestConfigurationFactory;

/**
 * Unit test for simple App.
 */
public class AppTest {

	EasyContext context = new EasyApplicationOnAnnotation();

	EasyService firstDemoService = new DemoEasyService();

	@Before
	public void setUp() {
		firstDemoService.configurePath("demo1");
		context.register(firstDemoService);
	}

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

	@Test(expected = EasyRequestException.class)
	public void requestThrowsExceptionIfThereIsAnyServiceThatCanHandleIt_withValidate_rootPath()
			throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("good");
		context.register(firstDemoService);
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response = request.to("bad/cant_handle").send().validate().get();
		Assert.assertTrue(response.getStatus().equals(EasyStatus.REJECTED));
	}

	@Test(expected = EasyRequestException.class)
	public void requestThrowsExceptionIfThereIsService_butCantHandleIt_withValidate()
			throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("bad");
		context.register(firstDemoService);
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response = request.to("bad/forSureServiceCantHandleIt").send().validate().get();
		Assert.assertTrue(response.getStatus().equals(EasyStatus.REJECTED));
	}

	@Test()
	public void requestDoesNotThrowExceptionIfThereIsAnyServiceThatCanHandleIt_withoutValidate_rootPath()
			throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("good");
		context.register(firstDemoService);
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response = request.to("bad/cant_handle").send().get();
		Assert.assertTrue(response.getStatus().equals(EasyStatus.REJECTED));
	}

	@Test()
	public void requestDoesNotThrowsExceptionIfThereIsService_butCantHandleIt_withoutValidate()
			throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("bad");
		context.register(firstDemoService);
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response = request.to("bad/forSureServiceCantHandleIt").send().get();
		Assert.assertTrue(response.getStatus().equals(EasyStatus.REJECTED));
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
		System.out.println("HERE1: " + response1.getAccepted() + " " + response1.getExecutionTimeMillis());
		Assert.assertTrue(response1.getStatus().equals(EasyStatus.DONE));
		System.out.println("HERE2: " + response2.getAccepted() + " " + response2.getExecutionTimeMillis());
		Assert.assertTrue(response2.getStatus().equals(EasyStatus.DONE));
	}

	@Ignore
	@Test
	public void serviceRequestValid_betweenService() throws URISyntaxException {
		EasyContext context = new EasyApplicationOnAnnotation();
		DemoEasyService workerDemoService = new DemoEasyService();
		workerDemoService.configurePath("DEMO");
		context.register(workerDemoService);

		DemoServiceView guiDemoService = new DemoServiceView();
		guiDemoService.configurePath("GUI");
		context.register(guiDemoService);
		workerDemoService.simpleMethod();
		workerDemoService.simpleMethod();
		workerDemoService.simpleMethod();
		workerDemoService.simpleMethod();
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response1 = request.to("DEMO/demo_path_nope/sleep").send().get();
		try {
			Thread.sleep(5000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void serviceRequestValid_actionAfterTaskComplete() throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		DemoEasyService workerDemoService = new DemoEasyService();
		workerDemoService.configurePath("DEMO");
		context.register(workerDemoService);

		DemoServiceView guiDemoService = new DemoServiceView();
		guiDemoService.configurePath("GUI");
		context.register(guiDemoService);
		workerDemoService.simpleMethod();
		workerDemoService.simpleMethod();
		workerDemoService.simpleMethod();
		workerDemoService.simpleMethod();
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response1 = request.to("DEMO/demo_path_nope/sleep").send().validate()
				.andDoWhenRespond(new DemoEasyAction()).get();

		try {
			Thread.sleep(7000);
			System.out.println("HERE:" + response1.getAccepted());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void serviceRequestValid_responseAfter1s() throws URISyntaxException, EasyRequestException {
		EasyContext context = new EasyApplicationOnAnnotation();
		EasyService firstDemoService = new DemoEasyService();
		firstDemoService.configurePath("demo1");
		context.register(firstDemoService);
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		EasyResponse response1 = request.to("demo1/demo_path_nope/sleep").send().validate().get();
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	@Test()
	public void serviceRequestValid_responseWithPassedArguments() throws URISyntaxException, EasyRequestException {
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		for (int i = 0; i < 9000; i++) {

			EasyResponse response1 = request.to("demo1/demo_path_nope/args").send("keks", 4).validate().get();
			// try {
			// Thread.sleep(40000);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
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
		// request.onResponseFrom(requestPath).thenDo(new DemoEasyAction());
		ResponseWithTimeout responseWithTimeOut = new ResponseWithTimeout(responseTimeout);
	}
}
