package com.easyrr.EasyResponse.request;

import com.easyrr.EasyResponse.EasyAction;
import com.easyrr.EasyResponse.ResponseStream;

public interface AcceptedRequestStream extends RequestStream {
	ResponseStream andDoWhenRespond(EasyAction demoEasyAction);
}
