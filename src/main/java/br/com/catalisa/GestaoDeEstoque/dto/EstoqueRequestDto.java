package br.com.catalisa.GestaoDeEstoque.dto;

import java.math.BigDecimal;

public record EstoqueRequestDto(
        Long produtoId,
        int quantidade,
        BigDecimal valorCusto,
        BigDecimal valorVenda,
        String lote,
        String validade) {
}
