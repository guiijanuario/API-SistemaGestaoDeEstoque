package br.com.catalisa.GestaoDeEstoque.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_FORNECEDORES")
public class FornecedorModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[^0-9]*$", message = "O nome não deve conter números")
    @Schema(description = "Nome do Fornecedor", example = "Pet Ingá LTDA")
    private String nome;

    @Schema(description = "Telefone", example = "(44) 99999-9999)")
    private String telefone;

    @Pattern(regexp = "^[0-9]{5}-?[0-9]{3}$", message = "O CEP deve estar no formato '87045650' ou '87045-650'")
    @Schema(description = "CEP fornecedor", example = "87045-100 ou 87045111")
    private String cep;

    @Pattern(regexp = "^[0-9]{1,6}$", message = "O campo deve conter somente números e ter no máximo 6 dígitos")
    @Schema(description = "Número do endereço", example = "100")
    private String nro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cep_id")
    //@Schema(hidden = true)
    private CepModel cepModel;
}
