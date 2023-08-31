package br.com.catalisa.GestaoDeEstoque.service;

import br.com.catalisa.GestaoDeEstoque.exception.BadRequestException;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.repository.EstoqueRepository;
import br.com.catalisa.GestaoDeEstoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public List<EstoqueModel> listarTodoEstoque() {
        return estoqueRepository.findAll();
    }
    public EstoqueModel criarEntradaEstoque(ProdutoModel produto, int quantidade, BigDecimal valorCusto, BigDecimal valorVenda, String lote, LocalDate validade) {
        EstoqueModel entradaEstoque = new EstoqueModel();
        entradaEstoque.setProduto(produto);
        entradaEstoque.setQuantidade(quantidade);
        entradaEstoque.setValorCusto(valorCusto);
        entradaEstoque.setValorVenda(valorVenda);
        entradaEstoque.setLote(lote);
        entradaEstoque.setValidade(validade);
        entradaEstoque.setDataHoraInsercao(LocalDateTime.now());
        return estoqueRepository.save(entradaEstoque);
    }

    public void listarEstoquePeloId(Long id){
        estoqueRepository.findById(id);
    }

    public void removerDoEstoque(Long id) {
        estoqueRepository.deleteById(id);
    }

    public void alterarQuantidadeEstoque(Long id, int novaQuantidade) {
        Optional<EstoqueModel> entradaEstoqueOptional = estoqueRepository.findById(id);
        if (entradaEstoqueOptional.isPresent()) {
            EstoqueModel entradaEstoque = entradaEstoqueOptional.get();
            entradaEstoque.setQuantidade(novaQuantidade);
            estoqueRepository.save(entradaEstoque);
        }
    }

    public void retirarQuantidadeEstoquePorProdutoId(Long produtoId, int quantidadeRetirada) {
        List<EstoqueModel> estoqueList = estoqueRepository.findByProdutoId(produtoId);

        int quantidadeTotal = 0;

        // somar todos os registros que tem daquele produto em estoque.
        for (EstoqueModel estoque : estoqueList) {
            quantidadeTotal += estoque.getQuantidade();
        }

        if (quantidadeTotal < quantidadeRetirada) {
            throw new BadRequestException("Não tem essa quantidade em estoque! Tem apenas: " + quantidadeTotal);
        }

        for (EstoqueModel estoque : estoqueList) {
            int quantidadeAtual = estoque.getQuantidade();
            int quantidadeFinal = quantidadeAtual - quantidadeRetirada;

            if (quantidadeFinal >= 0) {
                estoque.setQuantidade(quantidadeFinal);
                estoque.setDataHoraRetirada(LocalDateTime.now());
                estoque.setQuantidadeDaUltimaRetirada(quantidadeRetirada);
                estoqueRepository.save(estoque);
                quantidadeRetirada -= quantidadeAtual;
                if (quantidadeRetirada == 0) {
                    break;
                }
            } else {
                estoque.setQuantidade(0);
                estoqueRepository.save(estoque);
                quantidadeRetirada -= quantidadeAtual;
            }
        }
    }
}