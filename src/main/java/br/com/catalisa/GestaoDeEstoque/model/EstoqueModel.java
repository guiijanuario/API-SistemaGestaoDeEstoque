package br.com.catalisa.GestaoDeEstoque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_ESTOQUE")
public class EstoqueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoModel produto;

    private int quantidade;

    @Column(name = "valor_custo")
    private BigDecimal valorCusto;

    @Column(name = "valor_vendas")
    private BigDecimal valorVenda;

    private String lote;
    private LocalDate validade;

    @Column(name = "data_hora_insercao")
    private LocalDateTime dataHoraInsercao;

    @Column(name = "data_hora_retirada")
    private LocalDateTime dataHoraRetirada;

    @Column(name = "quantidade_ultima_retirada")
    private int quantidadeDaUltimaRetirada;


}
