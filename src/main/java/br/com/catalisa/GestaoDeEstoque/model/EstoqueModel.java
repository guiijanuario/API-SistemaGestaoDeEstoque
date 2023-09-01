package br.com.catalisa.GestaoDeEstoque.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_ESTOQUE")
public class EstoqueModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    @Schema(description = "ID do produto", example = "1")
    private ProdutoModel produto;

    @NotEmpty
    @Schema(description = "Quantidade do produto", example = "30")
    private int quantidade;

    @Column(name = "custo_do_produto")
    //@JsonProperty("custo_do_produto")
    @Schema(description = "Informar o custo de cada unidade", example = "30.0")
    private BigDecimal valorCusto;

    @Column(name = "valor_de_venda")
    //@JsonProperty("valor_de_venda")
    @Schema(description = "Informar o preço de venda de cada unidade", example = "50.0")
    private BigDecimal valorVenda;

    @Schema(description = "Informar o Lote do produto", example = "Lote123")
    private String lote;

    @Schema(description = "Informar a data de validade do produto", example = "2023/12/31")
    private LocalDate validade;

    @Column(name = "data_hora_insercao")
    //@JsonProperty("data_hora_insercao")
    @Schema(hidden = true)
    private LocalDateTime dataHoraInsercao;

    @Column(name = "data_hora_retirada")
    //@JsonProperty("data_hora_retirada")
    @Schema(hidden = true)
    private LocalDateTime dataHoraRetirada;

    @Column(name = "quantidade_ultima_retirada")
    //@JsonProperty("quantidade_ultima_retirada")
    @Schema(hidden = true)
    private int quantidadeDaUltimaRetirada;
}
