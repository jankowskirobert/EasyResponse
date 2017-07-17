package com.easyrr.EasyResponse;

public class DemoEasyService implements EasyService {

	public String getServicePath() {
		return "demo";
	}

	@EasyRegistredAction(path = "demo_path_nope")
	public void simpleMethod(){
		System.out.println("I WONT BACK");
	}
	
	@EasyRegistredAction(path = "demo_path_yep")
	public String simpleMethodWihtReturn(){
		String response = "I WILL BACK";
		System.out.println(response);
		return response;
	}
	
}
