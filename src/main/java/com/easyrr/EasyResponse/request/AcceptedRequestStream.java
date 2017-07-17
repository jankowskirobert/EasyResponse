package com.easyrr.EasyResponse.request;

import com.easyrr.EasyResponse.EasyAction;
import com.easyrr.EasyResponse.EasyResponse;
import com.easyrr.EasyResponse.ResponseStream;

public interface AcceptedRequestStream {
	EasyResponse get();
	ResponseStream andDoWhenRespond(EasyAction demoEasyAction);
}
