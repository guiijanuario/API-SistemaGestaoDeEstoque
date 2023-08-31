package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.enums.TipoLogEvento;
import br.com.catalisa.GestaoDeEstoque.exception.BadRequestException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
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

    @Autowired
    private LogEventosService logEventosService;

    @GetMapping
    @ResponseBody
    @Operation(summary = " : Lista todos os Produtos", method = "GET")
    public List<ProdutoModel> listarProdutos() {
        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_PRODUTOS);
        return produtoService.getAllProdutos();
    }

    @GetMapping("/{id}")
    public ProdutoModel getProdutoById(@PathVariable Long id) {
        ProdutoModel produto = produtoService.getProdutoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        logEventosService.gerarLogBuscaDePeloId(produto,TipoLogEvento.LISTOU_PRODUTO);
        return produto;
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> createProduto(@RequestBody ProdutoModel produto) {
            if (produto.getId() != null) {
                throw new BadRequestException("Não inclua ID ao criar um novo produto.");
            }
            ProdutoModel novoProduto = produtoService.createProduto(produto);
        logEventosService.gerarLogCadastroRealizado(novoProduto,TipoLogEvento.PRODUTO_CADASTRADO);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable Long id, @RequestBody ProdutoModel produtoAtualizado) {
        if (produtoAtualizado.getId() != null) {
            throw new BadRequestException("Não inclua ID ao alterar um novo produto.");
        }
        ProdutoModel updatedProduto = produtoService.updateProduto(id, produtoAtualizado);

        logEventosService.gerarLogAtualizacaoRealizada(updatedProduto,TipoLogEvento.PRODUTO_ALTERADO);

        return ResponseEntity.ok(updatedProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Insira o ID para deletar");
        }
        return ResponseEntity.noContent().build();
    }

}
