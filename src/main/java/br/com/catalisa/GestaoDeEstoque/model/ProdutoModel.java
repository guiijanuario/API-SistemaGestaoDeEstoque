package br.com.catalisa.GestaoDeEstoque.model;

import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_PRODUTOS")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_barras")
    private String codigoBarras;
    private String marca;
    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private CategoriaProduto categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorModel fornecedor;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<EstoqueModel> estoque;

}
