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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Feature - Estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LogEventosService logEventosService;


    @GetMapping("/listarEstoque")
    @Cacheable("estoques")
    @Operation(summary = "\uD83D\uDCC8 - Lista todos os estoques", description = "Lista todos os estoques cadastrado no sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoques listados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum estoque encontrado.")
    })
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
    @Operation(summary = "\uD83D\uDCC8 - Cadastra um estoque", description = "Faz entrada de um estoque no sistema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estoque cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estoque não cadastrado")
    })
    public EstoqueModel criarEntradaEstoque(@RequestBody EstoqueRequestDto request) {
        ProdutoModel produto = produtoService.getProdutoById(request.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        LocalDate dataValidade = LocalDate.parse(request.validade());
        EstoqueModel estoqueCriado = estoqueService.criarEntradaEstoque(produto, request.quantidade(), request.valorCusto(), request.valorVenda(), request.lote(), dataValidade);
        logEventosService.gerarLogCadastroRealizado(estoqueCriado, TipoLogEvento.ESTOQUE_CADASTRADO);
        return estoqueCriado;
    }


    @DeleteMapping("/remover/{id}")
    @Operation(summary = "\uD83D\uDCC8 - Deleta um estoque pelo ID", description = "Deleta um estoque", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum estoque encontrado.")
    })
    public void removerDoEstoque(@PathVariable Long id) {
        estoqueService.removerDoEstoque(id);
    }

    @PutMapping("/alterar-quantidade/{id}")
    @Operation(summary = "\uD83D\uDCC8 - Altera a quantidade do estoque pelo ID", description = "Altera o estoque de um ID do estoque especifico", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade do estoque alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Quantidade do estoque não alterado")
    })
    public void alterarQuantidadeEstoque(@PathVariable Long id, @RequestParam int novaQuantidade) {
        logEventosService.gerarLogListarAll(TipoLogEvento.ESTOQUE_ALTERADO);
        estoqueService.alterarQuantidadeEstoque(id, novaQuantidade);
    }

    @PutMapping("/retirarProduto/{id}/{qtdDeRetirada}")
    @Operation(summary = "\uD83D\uDCC8 - Faz a retirada de item no estoque pelo ID do produto/Quantidade", description = "Retira item do estoque pelo ID do produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foi retidado do estoque a quantidade informada"),
            @ApiResponse(responseCode = "404", description = "Não foi feito a retirada")
    })
    public void retirarQuantidadeDoProdutoNoEstoque(@PathVariable Long id, @PathVariable int qtdDeRetirada) {
      estoqueService.retirarQuantidadeEstoquePorProdutoId(id, qtdDeRetirada);
    }
}
