package com.licc.dove.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DoveAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {     
		
		  if(isAjax(request)){
			  Map<String,String> map=new HashMap<>();
	          map.put("code", "200");
	          map.put("msg", "登录成功");
	          response.setContentType("application/json;charset=UTF-8");
	          response.getWriter().write(objectMapper.writeValueAsString(map));
		  }else{
			//什么都不做的话，那就直接调用父类的方法
	          super.onAuthenticationSuccess(request, response, authentication);  
		  }
          //如果是要跳转到某个页面的，比如我们的那个whoim的则
//          new DefaultRedirectStrategy().sendRedirect(request, response, "/whoim");
    }
	
	public static boolean isAjax(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }
}
