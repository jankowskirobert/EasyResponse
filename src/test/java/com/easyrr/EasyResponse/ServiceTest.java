package com.easyrr.EasyResponse;

import org.junit.Test;

import com.easyrr.EasyResponse.application.EasyApplicationOnAnnotation;
import com.easyrr.EasyResponse.stream.EasyRequest;

public class ServiceTest {
	EasyContext context = new EasyApplicationOnAnnotation();
	EasyService firstDemoService = new DemoEasyService();	
	EasyService secondDemoService = new DemoEasyService();
	
	@Test
	public void testOneRequestToOneServiceServ(){
//		context.register(firstDemoService);
//		EasyResponse response = EasyRequest.through(context).to("url").sendAsync().get();
	}
}
