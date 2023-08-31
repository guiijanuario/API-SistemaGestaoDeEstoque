package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.exception.BadRequestException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Feature - Produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @GetMapping
    @ResponseBody
    @Operation(summary = " : Lista todos os Produtos", method = "GET")
    public List<ProdutoModel> listarProdutos() {
        return produtoService.getAllProdutos();
    }

    @GetMapping("/{id}")
    public ProdutoModel getProdutoById(@PathVariable Long id) {
        return produtoService.getProdutoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> createProduto(@RequestBody ProdutoModel produto) {
            if (produto.getId() != null) {
                throw new BadRequestException("Não inclua ID ao criar um novo produto.");
            }
            ProdutoModel novoProduto = produtoService.createProduto(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable Long id, @RequestBody ProdutoModel produtoAtualizado) {
        if (produtoAtualizado.getId() != null) {
            throw new BadRequestException("Não inclua ID ao alterar um novo produto.");
        }
        ProdutoModel updatedProduto = produtoService.updateProduto(id, produtoAtualizado);
        return ResponseEntity.ok(updatedProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Insira o ID para deletar");
        }
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

}
