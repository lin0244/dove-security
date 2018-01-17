package com.licc.dove.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.licc.dove.web.security.DoveSecurityManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private AuthenticationProvider provider;
	
	@Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	
	@Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailHander;
	
    @Autowired
    private DoveSecurityManager doveSecurityManager;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		// 添加验证码验证
        .formLogin().loginPage(doveSecurityManager.getLoginPageUrl())
        .loginProcessingUrl(doveSecurityManager.getDoLoginUrl())
        .authenticationDetailsSource(authenticationDetailsSource).permitAll()
//        .failureUrl("/login/loginError")  //表单登录，permitAll()表示这个不需要验证 登录页面，登录失败页面
        .successHandler(myAuthenticationSuccessHandler)
        .failureHandler(myAuthenticationFailHander)
        .and()

        .logout().addLogoutHandler(doveSecurityManager.getLogoutHanlder()).logoutUrl(doveSecurityManager.getDoLogoutUrl())
        .logoutSuccessUrl(doveSecurityManager.getLogoutSuccessUrl()).permitAll()
        .and()
        .authorizeRequests().antMatchers(doveSecurityManager.getStaticResourceMatcher()).permitAll()
				.antMatchers("/sys/dove/echo/**").permitAll()
				.and()

        // 其他url全部要求登录
        .authorizeRequests().anyRequest().authenticated().and()
        // 登录后的要求经过权限判断
        .authorizeRequests().anyRequest().access("@rbacService.hasPermission(request,authentication)")
        .and().anonymous().and()
        .headers()
			.xssProtection()
	        .and()
        .and()
        .csrf().disable();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.authenticationProvider(provider);
    }
}
