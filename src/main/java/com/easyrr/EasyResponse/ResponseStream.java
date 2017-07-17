package com.easyrr.EasyResponse;

public interface ResponseStream {

	void thenDo(EasyAction easyAction);

	EasyResponse get();

}
