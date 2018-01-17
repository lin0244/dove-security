package com.licc.dove.web.security;

import com.licc.dove.web.param.PageRes;
import com.licc.dove.web.param.PlatUserParam;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.licc.dove.web.param.PlatUser;

public interface DoveSecurityManager {
	//平台编码
	public String getPlat();

	public String getLoginPageUrl();
	
	public String getDoLoginUrl();
	
	public String getDoLogoutUrl();
	
	/**
	 * 退出成功后跳转的url
	 * @return
	 */
	public String getLogoutSuccessUrl();
	
	/**
	 * 表单提交登录时，登录失败跳转的页面，ajax登录时用不到这个
	 * @return
	 */
	public String getFormLoginFailUrl();
	
	public SecurityContextLogoutHandler getLogoutHanlder();
	
	public String[] getStaticResourceMatcher();

	/**
	 * Map<平台编码，用户列表>
	 * @return
	 */
	public PageRes<PlatUser> getPlatUsers(PlatUserParam param);
	
	/**
	 * 处理登录验证和获取权限信息的处理类
	 * @param <T>
	 * @return
	 */
	public <T> DoveAuthenticationHandler<T> getAuthenticationHandler();
}
