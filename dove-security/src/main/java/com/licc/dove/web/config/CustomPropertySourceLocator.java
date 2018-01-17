package com.licc.dove.web.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;


@Configuration
@Order(-1)
public class CustomPropertySourceLocator implements PropertySourceLocator {
    @Override
    public PropertySource<?> locate(Environment environment) {
        Map<String, Object> customMap = new HashMap<>();
        customMap.put("feign.hystrix.enabled",false);
        return new MapPropertySource("custom", customMap);
    }



}