package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.dto.EstoqueRequestDto;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.EstoqueService;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listarEstoque")
    public List<EstoqueModel> listarTodoEstoque() {
        return estoqueService.listarTodoEstoque();
    }

    @PostMapping("/entrada")
    public EstoqueModel criarEntradaEstoque(@RequestBody EstoqueRequestDto request) {
        ProdutoModel produto = produtoService.getProdutoById(request.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        LocalDate dataValidade = LocalDate.parse(request.validade());
        return estoqueService.criarEntradaEstoque(produto, request.quantidade(), request.valorCusto(), request.valorVenda(), request.lote(), dataValidade);
    }

    @DeleteMapping("/remover/{id}")
    public void removerDoEstoque(@PathVariable Long id) {
        estoqueService.removerDoEstoque(id);
    }

    @PutMapping("/alterar-quantidade/{id}")
    public void alterarQuantidadeEstoque(@PathVariable Long id, @RequestParam int novaQuantidade) {
        estoqueService.alterarQuantidadeEstoque(id, novaQuantidade);
    }

    @PutMapping("/retirarProduto/{id}/{qtdDeRetirada}")
    public void retirarQuantidadeDoProdutoNoEstoque(@PathVariable Long id, @PathVariable int qtdDeRetirada) {
        estoqueService.retirarQuantidadeEstoquePorProdutoId(id, qtdDeRetirada);
    }
}
