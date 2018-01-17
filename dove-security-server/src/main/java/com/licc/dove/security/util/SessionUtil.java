package com.licc.dove.security.util;


import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtil {
  @Bean
  public  static  HttpSession getSession(){
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();
    return servletRequestAttributes.getRequest().getSession();
  }
}
