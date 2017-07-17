package com.easyrr.EasyResponse;

public interface EasyService {
	public String getServicePath(); 
	public void updateServiceContext(EasyContext context);
	public void configurePath(String path);
}
