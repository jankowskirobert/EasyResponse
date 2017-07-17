package com.easyrr.EasyResponse;


import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	@Test
    public void testApp() throws URISyntaxException
    {
		EasyContext context = new EasyApplicationOnAnnotation();
		long responseTimeout = 1000; //ms
		
		//insulation
		EasyService firstDemoService = new DemoEasyService();
		context.register(firstDemoService);
		//insulation
		
		EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
		URI requestPath = new URI("demo/demo_path_nope");
		EasyResponse response = request.send(requestPath).andDoWhenRespond(new DemoEasyAction()).get();
		request.onResponseFrom(requestPath).thenDo(new DemoEasyAction());
		ResponseWithTimeout responseWithTimeOut = new ResponseWithTimeout(responseTimeout);
    }
}
