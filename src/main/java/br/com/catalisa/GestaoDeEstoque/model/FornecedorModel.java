package br.com.catalisa.GestaoDeEstoque.model;

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
    private String nome;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String nroEnd;
    private String cep;
    private String cidade;
    private String estado;
}
