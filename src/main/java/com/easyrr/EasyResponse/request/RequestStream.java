package com.easyrr.EasyResponse.request;

import com.easyrr.EasyResponse.DemoEasyAction;
import com.easyrr.EasyResponse.ResponseStream;

public interface RequestStream {

	

	AcceptedRequestStream send();	
	RequestStream validate() throws EasyRequestException;
}
