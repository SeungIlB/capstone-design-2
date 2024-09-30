package com.medical_web_service.capstone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    @Bean
    // 운영 환경에서는 Swagger를 비활성화하는 설정
    @Profile("!Prod")
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Telemedicine Web Services")
                        .description("Backend API documentation")
                        .version("1.0"));
    }
}