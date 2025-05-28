package com.project.ibtss.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "IBTSS"
                ),
                description = "Interprovincial Bus Ticket Sales System",
                title = " Interprovincial Bus Ticket Sales System"
        ),
        security = { //mở tính năng cho nhập token swagger
                @SecurityRequirement(name = "BearerAuth")
        }
)

@SecurityScheme(
        name = "BearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)


public class SwaggerConfig {
    @Value("${server.host.url}")
    private String HOST_URL;

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .addServersItem(new Server().url(HOST_URL).description("Localhost server"));
    }
}

