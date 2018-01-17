package com.licc.dove.web.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedMethod implements GrantedAuthority {

	/**
	 */
	private static final long serialVersionUID = 1490168253390838022L;
	private String methodUrl;
	@Override
	public String getAuthority() {
		return methodUrl;
	}

	public GrantedMethod(String methodUrl){
		this.methodUrl = methodUrl;
	}
}
