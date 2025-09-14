package miage.abdelali.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        final String schemeName = "bearerAuth";
        return new OpenAPI()
            .info(new Info()
                .title("SelfTarificateur API")
                .version("v1")
                .description("API de tarification des produits de prévoyance"))
            .components(new Components()
                .addSecuritySchemes(schemeName, new SecurityScheme()
                    .name(schemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
