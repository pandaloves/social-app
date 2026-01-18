package se.jensen.meiying.socialapp.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link OpenApiConfig} konfigurerar OpenAPI (Swagger) för projektet.
 * <p>
 * Klassen definierar API-information såsom titel och version,
 * samt säkerhetsinställningar för JWT-baserad autentisering i Swagger UI.
 * </p>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Skapar och returnerar en {@link OpenAPI}-instans med projektets metadata och säkerhetskonfiguration.
     * <p>
     * Konfigurationen inkluderar:
     * <ul>
     *     <li>API-titel och version.</li>
     *     <li>JWT-baserad Bearer-autentisering som säkerhetsschema.</li>
     * </ul>
     * </p>
     *
     * @return en {@link OpenAPI}-instans konfigurerad med API-info och säkerhetsinställningar.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Project3 API med JWT")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
