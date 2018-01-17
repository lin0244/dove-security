package com.licc.dove.web.config;

import feign.Contract;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigure {
     public static int connectTimeOutMillis = 12000;
     public static int readTimeOutMillis = 12000;
     @Bean
     public Request.Options options() {
            return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
     }
     @Bean
     Logger.Level feignLoggerLevel() {
          return Logger.Level.FULL;
     }
}