package br.com.catalisa.GestaoDeEstoque.dto;

import java.math.BigDecimal;

public record EstoqueListDto(ProdutoResponseEstoqueDto produto, int quantidade_em_estoque, BigDecimal valor_venda) {
}
