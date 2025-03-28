package com.gdgknu.Inveed.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPiConfig() {
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8080").description("Local Server"));
        servers.add(new Server().url("배포용 서버 주소가 들어갈 곳").description("Dev Server"));

        String jwtSchemeName = "bearerAuth";
        SecurityRequirement securityItem = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(new Info()
                        .title("Inveed Server")
                        .description("APIs for Inveed")
                        .license(new License()
                                .url("https://inveed.com")
                                .name("Inveed License"))
                        .contact(new Contact()
                                .name("Inveed Dev Team"))
                        .version("1.0.0"))
                .addSecurityItem(securityItem)
                .components(components)
                .servers(servers);
    }
}