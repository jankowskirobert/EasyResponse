package com.easyrr.EasyResponse.request;

import com.easyrr.EasyResponse.DemoEasyAction;
import com.easyrr.EasyResponse.ResponseStream;

public interface RequestStream {

	ResponseStream andDoWhenRespond(DemoEasyAction demoEasyAction);

	RequestStream send();	
	RequestStream validate() throws EasyRequestException;
}
