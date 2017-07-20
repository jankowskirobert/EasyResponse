package com.easyrr.EasyResponse;

import com.easyrr.EasyResponse.request.EasyRequestException;

public interface ResponseStream {

//	void thenDo(EasyAction easyAction);
	ResponseStream validate() throws EasyRequestException;
	public ResponseStream andDoWhenRespond(EasyAction demoEasyAction);
	EasyResponse get();

}
