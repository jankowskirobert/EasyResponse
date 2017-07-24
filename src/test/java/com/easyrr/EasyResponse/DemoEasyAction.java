package com.easyrr.EasyResponse;

public class DemoEasyAction implements EasyAction {

	@Override
	public void execute(EasyResponse response) {
		System.out.println("ATFER ALL: " + response.getAccepted());
	}

}
