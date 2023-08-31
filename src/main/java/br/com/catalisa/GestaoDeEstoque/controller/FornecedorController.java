package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.dto.CepResponseDto;
import br.com.catalisa.GestaoDeEstoque.dto.FornecedorResponseDto;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private CepValidations cepValidations;

    @GetMapping
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
        return ResponseEntity.ok(fornecedoresResponseDto);
    }

    @GetMapping("/{id}")
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

        return ResponseEntity.ok(fornecedorResponseDto);
    }

    public ResponseEntity<?> listarFornecedorId(@PathVariable Long id){
        Optional<FornecedorModel> fornecedorEncontrado = fornecedorService.getFornecedorById(id);

        if(fornecedorEncontrado.isEmpty()){
            FornecedorModel fornecedorNull = new FornecedorModel();
           // logEventosService.gerarLogBuscaDePeloId(alunoNull, TipoLogEvento.ALUNO_NAO_ENCONTRADO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado, tente novamente!");
        }

        FornecedorModel fornecedor = fornecedorEncontrado.orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado"));

      // logEventosService.gerarLogBuscaDePeloId(fornecedor, TipoLogEvento.LISTOU_ALUNO);

        return ResponseEntity.ok(fornecedorEncontrado.get());
    }


    @PostMapping
    public ResponseEntity<FornecedorModel> createFornecedor(@RequestBody FornecedorModel fornecedor) {
        FornecedorModel createdFornecedor = fornecedorService.createFornecedor(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorModel> updateFornecedor(@PathVariable Long id, @RequestBody FornecedorModel fornecedorAtualizado) {
        FornecedorModel updatedFornecedor = fornecedorService.updateFornecedor(id, fornecedorAtualizado);
        return ResponseEntity.ok(updatedFornecedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        fornecedorService.deleteFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
