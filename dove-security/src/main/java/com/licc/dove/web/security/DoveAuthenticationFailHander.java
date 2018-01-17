package com.licc.dove.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DoveAuthenticationFailHander implements AuthenticationFailureHandler{

	@Autowired
    private ObjectMapper objectMapper;
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
          //logger.info("登录失败");
          //以Json格式返回
          Map<String,String> map=new HashMap<>();
          map.put("code", "201");
          map.put("msg", "登录失败");
          response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
          response.setContentType("application/json");
          response.setCharacterEncoding("UTF-8");   
          response.getWriter().write(objectMapper.writeValueAsString(map));
          
    }
}
