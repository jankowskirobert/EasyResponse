package com.easyrr.EasyResponse;

import java.net.URI;
import java.util.Optional;

public abstract class EasyMessage {
	protected URI path;
	protected RequestObserver request;
	protected Optional<Object[]> params;
}
