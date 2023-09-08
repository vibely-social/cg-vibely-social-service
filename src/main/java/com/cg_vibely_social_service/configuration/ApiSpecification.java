package com.cg_vibely_social_service.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Vibely Social API",
                description = "API endpoints to get data for vibely social web/app",
                version = "1.0-alpha",
                contact = @Contact(
                        name = "Facogi Corporation",
                        url = "https://facogi.corp.com",
                        email = "vibely@facogi.com"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "REST Server URL"
                )
        },
        security = @SecurityRequirement(
                name = "BearerTokenAuth"
        )
)
@SecurityScheme(
        name = "BearerTokenAuth",
        description = "JWT Authorization",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class ApiSpecification {

}
