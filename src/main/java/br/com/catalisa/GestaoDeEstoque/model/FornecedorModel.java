package br.com.catalisa.GestaoDeEstoque.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_FORNECEDORES")
public class FornecedorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do Fornecedor", example = "asdoasjiodjasdjioasiod")
    private String nome;
    private String telefone;
    private String cep;
    private String nro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Endereço: {cep}, {logradouro}, {bairro}, {localidade} - {uf}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cep_id")
    private CepModel cepModel;



}
