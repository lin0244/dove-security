package com.licc.dove.web.security;

import java.util.List;
import java.util.Map;

public interface  DoveAuthenticationHandler<T> {

	public T doAuthentication(String username , String password , Map<String ,Object> details);
	
	public List<GrantedMethod> doAuthorization(T user); 
}
