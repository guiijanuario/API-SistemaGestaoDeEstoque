package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<FornecedorModel> getAllFornecedores() {
        return fornecedorService.getAllFornecedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorModel> getFornecedorById(@PathVariable Long id) {
        Optional<FornecedorModel> fornecedor = fornecedorService.getFornecedorById(id);
        return fornecedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FornecedorModel> createFornecedor(@RequestBody FornecedorModel fornecedor) {
        FornecedorModel createdFornecedor = fornecedorService.createFornecedor(fornecedor);
        return ResponseEntity.created(URI.create("/fornecedores/" + createdFornecedor.getId())).body(createdFornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorModel> updateFornecedor(
            @PathVariable Long id,
            @RequestBody FornecedorModel fornecedorAtualizado) {
        FornecedorModel updatedFornecedor = fornecedorService.updateFornecedor(id, fornecedorAtualizado);
        return ResponseEntity.ok(updatedFornecedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        fornecedorService.deleteFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
