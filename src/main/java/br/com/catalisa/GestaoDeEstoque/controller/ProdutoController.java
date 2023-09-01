package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.enums.TipoLogEvento;
import br.com.catalisa.GestaoDeEstoque.exception.BadRequestException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Operation(summary = "\uD83D\uDCE6 - Lista todos os Produtos", description = "Retorna uma lista de todos os produtos disponíveis.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado.")
    })
    @Cacheable("produtos")
    public List<ProdutoModel> listarProdutos() {
        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_PRODUTOS);
        return produtoService.getAllProdutos();
    }


    @GetMapping("/{id}")
    @Operation(summary = "\uD83D\uDCE6 - Lista produto pelo ID", description = "Retorna um produto pelo ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado.")
    })
    public ProdutoModel getProdutoById(@PathVariable Long id) {
        ProdutoModel produto = produtoService.getProdutoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        logEventosService.gerarLogBuscaDePeloId(produto,TipoLogEvento.LISTOU_PRODUTO);
        return produto;
    }

    @Operation(summary = "\uD83D\uDCE6 - Cadastra um produto", description = "Faz o cadastro de um produto no sistema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não cadastrado")
    })
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
    @Operation(summary = "\uD83D\uDCE6 - Altera um produto pelo ID", description = "Altera os dados de um produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não alterado")
    })
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable Long id, @RequestBody ProdutoModel produtoAtualizado) {
        if (produtoAtualizado.getId() != null) {
            throw new BadRequestException("Não inclua ID ao alterar um novo produto.");
        }
        ProdutoModel updatedProduto = produtoService.updateProduto(id, produtoAtualizado);
        logEventosService.gerarLogAtualizacaoRealizada(updatedProduto,TipoLogEvento.PRODUTO_ALTERADO);
        return ResponseEntity.ok(updatedProduto);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "\uD83D\uDCE6 - Deleta um produto pelo ID", description = "Deleta um produto", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado.")
    })
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Insira o ID para deletar");
        }
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

}
