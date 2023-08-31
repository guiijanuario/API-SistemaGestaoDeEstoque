package br.com.catalisa.GestaoDeEstoque.enums;

public enum CategoriaProduto {

    ELETRONICOS("Eletrônicos"),
    VESTUARIO("Vestuário"),
    ALIMENTOS("Alimentos"),
    BEBIDAS("Bebidas"),
    COSMETICOS("Cosméticos"),
    MOVEIS("Móveis"),
    JOGOS("Jogos"),
    BRINQUEDOS("Brinquedos"),
    PET("Pet"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
