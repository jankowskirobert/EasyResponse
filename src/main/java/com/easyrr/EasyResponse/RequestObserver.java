package com.easyrr.EasyResponse;

import java.net.URI;
import java.util.Optional;

public interface RequestObserver {
	void updateRequest(EasyStatus status, URI path, Optional<Long> endTime);
}
