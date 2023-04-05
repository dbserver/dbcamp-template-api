package com.template.config.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

// Para acessar o open api docs (swagger) vá para o link: http://localhost:4767/swagger-ui/index.html
// Para acessar os api docs vá para o link: http://localhost:4767/v3/api-docs

@Configuration
public class OpenApiConfig {

    @Value("${template.openapi.dev-url}")
    private String devUrl;

    @Value("${template.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL em Ambiente de desenvolvimento");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL em Ambiente de produção");

        Contact contact = new Contact();
        contact.setEmail("user@db.tec.cr");
        contact.setName("DbServer");
        contact.setUrl("https://www.dbserver.com.br");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Template Management API")
                .version("1.0")
                .contact(contact)
                .description("Esta api fornece acesso aos endpoints do template").termsOfService("https://www.fakedoc.com")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
