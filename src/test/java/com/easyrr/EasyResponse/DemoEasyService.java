package com.easyrr.EasyResponse;

import java.net.URISyntaxException;
import java.util.Random;

import com.easyrr.EasyResponse.stream.EasyRequest;
import com.easyrr.EasyResponse.stream.EasyRequestException;
import com.easyrr.EasyResponse.stream.RequestConfigurationFactory;

public class DemoEasyService implements EasyService {

	private String path;
	private EasyContext context;

	public String getServicePath() {
		return path;
	}

	@EasyRegistredAction(path = "demo_path_nope")
	public void simpleMethod() {
		try {
			EasyRequest request = new EasyRequest(context, new RequestConfigurationFactory());
			EasyResponse response1 = request.to("GUI/increase_value").send().get();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("I WONT BACK");
	}

	@EasyRegistredAction(path = "demo_path_nope/args")
	public void simpleMethod_args(String dupa, int i) {
		// try {
		// EasyRequest request = new EasyRequest(context, new
		// RequestConfigurationFactory());
		// EasyResponse response1 =
		// request.to("GUI/increase_value").send().get();
		// } catch ( URISyntaxException e) {
		// e.printStackTrace();
		// }
		System.out.println("WOWW: " + dupa + " " + i);
	}

	@EasyRegistredAction(path = "demo_path_nope/sleep")
	public void simpleMethod_ThreadSleep() {
		try {
			int period = new Random().nextInt(3000) + 1000;
			System.out.println("I WONT BACK - SLEEP: " + period);
			Thread.sleep(period);
			System.out.println("I WONT BACK - SLEEP");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@EasyRegistredAction(path = "demo_path_yep/ops")
	public String simpleMethodWihtReturn() {
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
