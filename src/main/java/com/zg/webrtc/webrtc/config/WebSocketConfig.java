package com.zg.webrtc.webrtc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@Order(10)
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        ServerEndpointExporter endpointExporter = new ServerEndpointExporter();
        return  endpointExporter;
    }
}
