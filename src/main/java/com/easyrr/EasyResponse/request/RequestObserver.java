package com.easyrr.EasyResponse.request;

import com.easyrr.EasyResponse.EasyStatus;

public interface RequestObserver {
	public void updateRequestStatus(EasyStatus status);
}
