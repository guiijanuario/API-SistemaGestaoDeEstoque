package br.com.catalisa.GestaoDeEstoque;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API - Gerenciamento de Estoque", version = "1", description = "API desenvolvida para gerenciar o estoque de uma empresa"))
@EnableCaching
public class GestaoDeEstoqueApplication {
	public static void main(String[] args) {
		SpringApplication.run(GestaoDeEstoqueApplication.class, args);
	}

}
