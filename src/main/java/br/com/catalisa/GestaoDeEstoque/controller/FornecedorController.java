package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.exception.CepInvalidoException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private CepValidations cepValidations;

    @GetMapping
    public ResponseEntity<List<FornecedorModel>> getAllFornecedores() {
        List<FornecedorModel> fornecedores = fornecedorService.getAllFornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorModel> getFornecedorById(@PathVariable Long id) {
        FornecedorModel fornecedor = fornecedorService.getFornecedorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));
        return ResponseEntity.ok(fornecedor);
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
