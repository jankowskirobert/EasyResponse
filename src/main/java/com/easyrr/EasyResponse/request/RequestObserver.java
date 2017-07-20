package com.easyrr.EasyResponse.request;

import java.net.URI;

import com.easyrr.EasyResponse.EasyStatus;

public interface RequestObserver {
	void updateRequest(EasyStatus status, URI path);
}
