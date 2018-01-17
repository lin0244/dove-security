package com.licc.dove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableEurekaServer
@SpringBootApplication

public class DoveSecurityEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoveSecurityEurekaApplication.class, args);
	}
}
