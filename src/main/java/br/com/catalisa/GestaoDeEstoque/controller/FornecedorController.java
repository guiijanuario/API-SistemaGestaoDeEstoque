package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.dto.CepResponseDto;
import br.com.catalisa.GestaoDeEstoque.dto.FornecedorResponseDto;
import br.com.catalisa.GestaoDeEstoque.enums.TipoLogEvento;
import br.com.catalisa.GestaoDeEstoque.exception.IdNaoEncontradoException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
@Tag(name = "Feature - Fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private CepValidations cepValidations;

    @Autowired
    private LogEventosService logEventosService;

    @GetMapping
    @Cacheable("produtos")
    @Operation(summary = "\uD83D\uDE9A - Lista todos os fornecedores", description = "Lista todos os fornecedores cadastrado no sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores listados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum fornecedor encontrado.")
    })
    public ResponseEntity<List<FornecedorResponseDto>> getAllFornecedores() {
        List<FornecedorModel> fornecedoresEncontrados = fornecedorService.getAllFornecedores();

        List<FornecedorResponseDto> fornecedoresResponseDto = new ArrayList<>();
        for (FornecedorModel fornecedor : fornecedoresEncontrados) {
            CepResponseDto cepResponseDto = new CepResponseDto(
                    fornecedor.getCepModel().getCep(),
                    fornecedor.getCepModel().getLogradouro(),
                    fornecedor.getCepModel().getBairro(),
                    fornecedor.getCepModel().getLocalidade(),
                    fornecedor.getCepModel().getUf());

            FornecedorResponseDto fornecedorResponseDto = new FornecedorResponseDto(
                    fornecedor.getNome(),
                    fornecedor.getTelefone(),
                    fornecedor.getCep(),
                    fornecedor.getNro(),
                    cepResponseDto);
            fornecedoresResponseDto.add(fornecedorResponseDto);
        }
        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_FORNECEDORES);
        return ResponseEntity.ok(fornecedoresResponseDto);
    }


    @GetMapping("/{id}")
    @Operation(summary = "\uD83D\uDE9A - Busca fornecedor pelo ID", description = "Retorna um fornecedor pelo ID fornecido", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor listado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum fornecedor encontrado.")
    })
    public ResponseEntity<FornecedorResponseDto> getFornecedorById(@PathVariable Long id) {
        FornecedorModel fornecedor = fornecedorService.getFornecedorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));

        CepResponseDto cepResponseDto = new CepResponseDto(
                fornecedor.getCepModel().getCep(),
                fornecedor.getCepModel().getLogradouro(),
                fornecedor.getCepModel().getBairro(),
                fornecedor.getCepModel().getLocalidade(),
                fornecedor.getCepModel().getUf());

        FornecedorResponseDto fornecedorResponseDto = new FornecedorResponseDto(
                fornecedor.getNome(),
                fornecedor.getTelefone(),
                fornecedor.getCep(),
                fornecedor.getNro(),
                cepResponseDto);
        logEventosService.gerarLogBuscaDePeloId(fornecedor, TipoLogEvento.LISTOU_FORNECEDOR);
        return ResponseEntity.ok(fornecedorResponseDto);
    }


    @PostMapping
    @Operation(summary = "\uD83D\uDE9A - Cadastra um fornecedor", description = "Faz o cadastro de um fornecedor no sistema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não cadastrado")
    })
    public ResponseEntity<FornecedorModel> createFornecedor(@RequestBody FornecedorModel fornecedor) {
        FornecedorModel createdFornecedor = fornecedorService.createFornecedor(fornecedor);
        logEventosService.gerarLogCadastroRealizado(createdFornecedor,TipoLogEvento.FORNECEDOR_CADASTRADO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFornecedor);
    }

//    @PutMapping("/{id}")
//    @Operation(summary = "\uD83D\uDE9A - Altera um fornecedor pelo ID", description = "Altera os dados de um fornecedor", method = "PUT")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Fornecedor alterado com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Fornecedor não alterado")
//    })
//    public ResponseEntity<FornecedorModel> updateFornecedor(@PathVariable Long id, @RequestBody FornecedorModel fornecedorAtualizado) {
//        FornecedorModel updatedFornecedor = fornecedorService.updateFornecedor(id, fornecedorAtualizado);
//
//
//        logEventosService.gerarLogAtualizacaoRealizada(updatedFornecedor, TipoLogEvento.FORNECEDOR_ALTERADO);
//        return ResponseEntity.ok(updatedFornecedor);
//    }

    @DeleteMapping("/{id}")
    @Operation(summary = "\uD83D\uDE9A - Deleta um fornecedor pelo ID", description = "Deleta um fornecedor", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum fornecedor encontrado.")
    })
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        Optional<FornecedorModel> fornecedorEncontrado = fornecedorService.getFornecedorById(id);
        FornecedorModel fornecedor = fornecedorEncontrado.orElseThrow(() -> new IdNaoEncontradoException("Fornecedor não encontrado!"));

        logEventosService.gerarLogDeleteRealizado(fornecedor, TipoLogEvento.FORNECEDOR_DELETADO);
        fornecedorService.deleteFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
