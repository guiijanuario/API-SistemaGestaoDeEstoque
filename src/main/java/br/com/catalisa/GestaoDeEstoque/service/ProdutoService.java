package br.com.catalisa.GestaoDeEstoque.service;

import br.com.catalisa.GestaoDeEstoque.exception.BadRequestException;
import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.repository.FornecedorRepository;
import br.com.catalisa.GestaoDeEstoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private final ProdutoRepository produtoRepository;

    @Autowired
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public Optional<ProdutoModel> getProdutoById(Long id) {
        return produtoRepository.findById(id);
    }

    public List<ProdutoModel> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public ProdutoModel createProduto(ProdutoModel produto) {
        FornecedorModel fornecedor = fornecedorRepository.findById(produto.getFornecedor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + produto.getFornecedor().getId()));

        produto.setFornecedor(fornecedor);

        return produtoRepository.save(produto);
    }

    public ProdutoModel updateProduto(Long id, ProdutoModel produtoAtualizado) {
        ProdutoModel produtoExistente = getProdutoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());

        return produtoRepository.save(produtoExistente);
    }

    public void deleteProduto(Long id) {
        ProdutoModel produto = getProdutoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        produtoRepository.delete(produto);
    }

    public void adicionarAoEstoque(Long id, int quantidade) {
        ProdutoModel produto = getProdutoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        produtoRepository.save(produto);
    }


}
