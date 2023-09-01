package br.com.catalisa.GestaoDeEstoque;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API - Gerenciamento de Estoque", version = "1", description = "Bem-vindo à API de Gestão de Estoques, uma aplicação desenvolvida com o Spring Framework e um banco de dados em memória H2 para gerenciar produtos e fornecedores em seu estoque. Esta API oferece endpoints RESTful para realizar operações de criação, leitura, atualização e exclusão de produtos, bem como operações relacionadas aos fornecedores e ao estoque. Aqui está um resumo das principais funcionalidades e recursos desta aplicação! \uD83D\uDE80\n" +
		"\n" +
		"Para acessar a API localmente, você pode usar o seguinte endpoint:\n" +
		"\n" +
		"- **Endpoint local**: `http://localhost:8080/`\n" +
		"## Instruções de Instalação e Execução\n" +
		"\n" +
		"## ⚙\uFE0F Como Executar a Aplicação\n" +
		"\n" +
		"1. **Pré-requisitos:**\n" +
		"    - Certifique-se de ter o [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) instalado em seu computador.\n" +
		"\n" +
		"2. **Clone o Repositório:**\n" +
		"    - Faça o clone do repositório do projeto para o seu ambiente de desenvolvimento [LINK REPOSITÓRIO](https://github.com/guiijanuario/SistemaGestaoDeEstoque.git)\n" +
		"\n" +
		"3. **Navegue até o Diretório:**\n" +
		"    - Abra o terminal e navegue até o diretório onde se encontra o arquivo `GestaoDeEstoqueApplication.java`.\n" +
		"\n" +
		"4. **Compilação:**\n" +
		"    - Compile o arquivo utilizando o seguinte comando:\n" +
		"      ```\n" +
		"      javac GestaoDeEstoqueApplication.java\n" +
		"      ```\n" +
		"\n" +
		"5. **Execução:**\n" +
		"    - Após compilar, execute a aplicação com o seguinte comando:\n" +
		"      ```\n" +
		"      java GestaoDeEstoqueApplication.java\n" +
		"      ```" +
		"\n ## \uD83D\uDC68\u200D\uD83D\uDCBB Autor\n" +
		"\n" +
		"Nome: Guilherme Januário <br>Linkedin: `https://www.linkedin.com/in/guilherme-janu%C3%A1rio/`\n" +
		"\n" +
		"---\n" +
		"\n" +
		"<h4>©\uFE0F Made with \uD83D\uDC9A by <a href=\"https://github.com/guiijanuario\">Guilherme Januário</a></h4>\n"))

@EnableCaching
public class GestaoDeEstoqueApplication {
	public static void main(String[] args) {
		SpringApplication.run(GestaoDeEstoqueApplication.class, args);
	}

}
