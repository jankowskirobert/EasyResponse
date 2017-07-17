package com.easyrr.EasyResponse;

public interface RequestStream {

	RequestStream andDoWhenRespond(DemoEasyAction demoEasyAction);

	EasyResponse get();	
}
