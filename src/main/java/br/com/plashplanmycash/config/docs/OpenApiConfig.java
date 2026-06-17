package br.com.plashplanmycash.config.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plash - Plan my Cash")
                        .version("v1.0.0")
                        .description("API REST para gerenciamento financeiro pessoal"))
                .tags(List.of(
                        new Tag()
                                .name("Autenticação")
                                .description("Operações de login e registro de usuários"),
                        new Tag()
                                .name("Usuários")
                                .description("Gerenciamento de dados do usuário autenticado"),
                        new Tag()
                                .name("Carteiras")
                                .description("Gerenciamento de carteiras e contas financeiras"),
                        new Tag()
                                .name("Planejamentos")
                                .description("Gerenciamento de metas e planejamentos financeiros")
                ));
    }
}
