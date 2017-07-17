package com.easyrr.EasyResponse;

public class DemoEasyService implements EasyService {

	private String path;
	private EasyContext context;

	public String getServicePath() {
		return path;
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

	public void configurePath(String path) {
		this.path = path;		
	}

	public void updateServiceContext(EasyContext context) {
		this.context = context;	
	}
	
}
