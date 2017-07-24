package com.easyrr.EasyResponse.stream;

import java.util.function.Consumer;

import com.easyrr.EasyResponse.EasyAction;
import com.easyrr.EasyResponse.EasyResponse;

public interface ResponseStream {

//	void thenDo(EasyAction easyAction);
	ResponseStream validate() throws EasyRequestException;
	ResponseStream andDoWhenRespond(EasyAction demoEasyAction);
	EasyResponse get();

}
