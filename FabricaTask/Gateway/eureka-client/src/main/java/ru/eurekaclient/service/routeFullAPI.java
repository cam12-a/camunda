package ru.eurekaclient.service;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

public class routeFullAPI {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                        "r",f->f.path("/api/question/**")
                                .uri("http://localhost:8089")
                ).build();

    }

}
