package br.com.catalisa.GestaoDeEstoque.dto;

public record FornecedorResponseDto(String nome, String telefone, String cep, String numero, CepResponseDto endereco_completo) {
}
