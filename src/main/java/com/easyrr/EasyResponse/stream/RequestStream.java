package com.easyrr.EasyResponse.stream;

import com.easyrr.EasyResponse.DemoEasyAction;

public interface RequestStream {
	ResponseStream send(Object... objects);	
}
