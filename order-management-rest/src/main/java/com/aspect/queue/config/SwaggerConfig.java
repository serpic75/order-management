package com.aspect.queue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@ConditionalOnProperty("swagger.enabled")
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI(@Value("${server.contextPath}") String contextPath) {
        return new OpenAPI()
                .addServersItem(new Server().url(contextPath))
                .info(new Info().title("CONNECTED SOLUTIONS REST API")
                        .description("REST API for interacting with Order Management App. ")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation().description("My Apps API terms of service")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));
    }
}
