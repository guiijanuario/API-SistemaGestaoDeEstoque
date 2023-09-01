package br.com.catalisa.GestaoDeEstoque.model;

import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_PRODUTOS")
public class ProdutoModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_barras")
    //@JsonProperty("codigo_barras")
    @Schema(description = "Código de barras", example = "13 dígitos numérico")
    private String codigoBarras;

    @Schema(description = "Marca do Produto", example = "Cacal Show")
    private String marca;

    @Schema(description = "Nome do Produto", example = "Trufa ao leite")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Esse produto é vegano")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Categoria do produto", example = "ALIMENTACAO")
    private CategoriaProduto categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    @Schema(description = "ID do Fornecedor", example = "1")
    private FornecedorModel fornecedor;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<EstoqueModel> estoque;

}
