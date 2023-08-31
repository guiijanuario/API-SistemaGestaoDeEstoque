package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.dto.EstoqueListDto;
import br.com.catalisa.GestaoDeEstoque.dto.EstoqueRequestDto;
import br.com.catalisa.GestaoDeEstoque.dto.ProdutoResponseEstoqueDto;
import br.com.catalisa.GestaoDeEstoque.enums.TipoLogEvento;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.EstoqueService;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LogEventosService logEventosService;


    @GetMapping("/listarEstoque")
    @Operation(summary = " : Listar todo o estoque", method = "GET")
    @Cacheable("estoques")
    public ResponseEntity<List<EstoqueListDto>> listarTodosEstoques() {
        List<EstoqueModel> estoqueEncontrados = estoqueService.listarTodoEstoque();
        List<EstoqueListDto> estoqueListDtos = new ArrayList<>();
        for (EstoqueModel estoqueProduto : estoqueEncontrados) {
            ProdutoResponseEstoqueDto produtoDto = new ProdutoResponseEstoqueDto(
                    estoqueProduto.getProduto().getCodigoBarras(),
                    estoqueProduto.getProduto().getMarca(),
                    estoqueProduto.getProduto().getNome(),
                    estoqueProduto.getProduto().getCategoria());

            EstoqueListDto estoqueDto = new EstoqueListDto(
                    produtoDto,
                    estoqueProduto.getQuantidade(),
                    estoqueProduto.getValorVenda());
            estoqueListDtos.add(estoqueDto);
        }
       logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_TODO_ESTOQUE);
        return ResponseEntity.ok(estoqueListDtos);
    }

    @PostMapping("/entrada")
    public EstoqueModel criarEntradaEstoque(@RequestBody EstoqueRequestDto request) {
        ProdutoModel produto = produtoService.getProdutoById(request.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        LocalDate dataValidade = LocalDate.parse(request.validade());
        EstoqueModel estoqueCriado = estoqueService.criarEntradaEstoque(produto, request.quantidade(), request.valorCusto(), request.valorVenda(), request.lote(), dataValidade);
        logEventosService.gerarLogCadastroRealizado(estoqueCriado, TipoLogEvento.ESTOQUE_CADASTRADO);
        return estoqueCriado;
    }

    @DeleteMapping("/remover/{id}")
    public void removerDoEstoque(@PathVariable Long id) {
        estoqueService.removerDoEstoque(id);
    }

    @PutMapping("/alterar-quantidade/{id}")
    public void alterarQuantidadeEstoque(@PathVariable Long id, @RequestParam int novaQuantidade) {
        logEventosService.gerarLogListarAll(TipoLogEvento.ESTOQUE_ALTERADO);
        estoqueService.alterarQuantidadeEstoque(id, novaQuantidade);
    }

    @PutMapping("/retirarProduto/{id}/{qtdDeRetirada}")
    public void retirarQuantidadeDoProdutoNoEstoque(@PathVariable Long id, @PathVariable int qtdDeRetirada) {
      estoqueService.retirarQuantidadeEstoquePorProdutoId(id, qtdDeRetirada);
    }
}
