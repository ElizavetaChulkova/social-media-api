package ru.chulkova.socialmediaapi.config;

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
                contact = @Contact(
                        name = "Chulkova Elizaveta",
                        email = "chulkova.liza.w@gmail.com",
                        url = "https://github.com/ElizavetaChulkova"
                ),
                description = "OpenApi documentation for project 'Social Media API'",
                title = "Social Media API OpenApi specification"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
//                @Server(
//                        description = "PROD ENV",
//                        url = "https://aliboucoding.com/course"
//                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
