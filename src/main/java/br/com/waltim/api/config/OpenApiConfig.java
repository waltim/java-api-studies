package br.com.waltim.api.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restful API")
                        .version("v1")
                        .description("Application for studying Java")
                        .termsOfService("https://github.com/waltim/java-api-studies")
                        .license(new License().name("Apache 2.0")
                        .url("https://github.com/waltim/java-api-studies")));
    }
}
