package com.example.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${server.port}")
    private int serverPort;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(serverPort);
    }
}
