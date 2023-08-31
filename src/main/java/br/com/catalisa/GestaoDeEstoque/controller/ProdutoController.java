package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.exception.BadRequestException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

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

    @GetMapping
    public String listarProdutos(Model model) {
        List<ProdutoModel> produtos = produtoService.getAllProdutos();
        model.addAttribute("produtos", produtos);
        return "produtos";
    }
}
