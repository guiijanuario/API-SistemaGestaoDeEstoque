package br.com.catalisa.GestaoDeEstoque.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[^0-9]*$", message = "O nome não deve conter números")
    @Schema(description = "Nome do Fornecedor", example = "Pet Ingá LTDA")
    private String nome;

    @Schema(description = "Nome do Fornecedor", example = "Pet Ingá LTDA")
    private String telefone;

    @Pattern(regexp = "^[0-9]{5}-?[0-9]{3}$", message = "O CEP deve estar no formato '87045650' ou '87045-650'")
    private String cep;

    @Pattern(regexp = "^[0-9]{1,6}$", message = "O campo deve conter somente números e ter no máximo 6 dígitos")
    private String nro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cep_id")
    private CepModel cepModel;



}
