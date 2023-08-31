package br.com.catalisa.GestaoDeEstoque.dto;


import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;

public record ProdutoResponseEstoqueDto(String codigo_barras, String marca, String nome, CategoriaProduto categoria) {
}
