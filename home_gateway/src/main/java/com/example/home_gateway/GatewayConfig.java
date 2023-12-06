package com.example.home_gateway;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r
                        .path("/api/auth/**", "/api/get/**", "/api/refresh/**", "/api/signout/**", "/api/delete/**", "/api/register/**")
                        .uri("http://localhost:8060"))
                .route("home", r -> r
                        .path("/api/**")
                        .uri("http://localhost:8050"))
                .build();
    }
}
