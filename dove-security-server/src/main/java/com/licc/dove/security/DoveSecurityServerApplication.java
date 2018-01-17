package com.licc.dove.security;

import com.licc.dove.security.config.MvcConfig;
import com.licc.dove.security.config.MyBatisConfig;
import com.licc.dove.security.config.SecurityConfig;
import com.licc.dove.security.config.SessionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@Configuration
@EnableAutoConfiguration
@EnableFeignClients
@EnableHystrix
@Import({
		MvcConfig.class,
		MyBatisConfig.class,
		SecurityConfig.class,
		SessionConfig.class
})
@ComponentScan("com.jumore")
public class DoveSecurityServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoveSecurityServerApplication.class, args);
	}
}
