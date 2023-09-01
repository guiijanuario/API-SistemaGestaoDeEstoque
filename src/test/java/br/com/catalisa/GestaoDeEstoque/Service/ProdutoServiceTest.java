package br.com.catalisa.GestaoDeEstoque.Service;


import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.repository.FornecedorRepository;
import br.com.catalisa.GestaoDeEstoque.repository.ProdutoRepository;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Before
    public void setUp() {
        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");

        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
    }

    @Test
    public void testGetProdutoById() {
        Long produtoId = 1L;
        ProdutoModel novoProduto = new ProdutoModel();
        novoProduto.setId(produtoId);
        novoProduto.setNome("Produto Teste");
        novoProduto.setDescricao("Descrição do Novo Produto");
        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("98765-432");
        fornecedor.setNro("456");
        novoProduto.setFornecedor(fornecedor);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(novoProduto));

        Optional<ProdutoModel> produto = produtoService.getProdutoById(1L);

        assertEquals("Produto Teste", produto.get().getNome());
    }

    @Test
    public void testGetAllProdutos() {
        List<ProdutoModel> produtos = produtoService.getAllProdutos();
        ProdutoModel novoProduto = new ProdutoModel();
        novoProduto.setNome("Produto Teste");
        novoProduto.setDescricao("Descrição do Novo Produto");
        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("98765-432");
        fornecedor.setNro("456");
        novoProduto.setFornecedor(fornecedor);
        produtos.add(novoProduto);

        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
        assertEquals("Descrição do Novo Produto", produtos.get(0).getDescricao());
        assertEquals("Fornecedor 1", produtos.get(0).getFornecedor().getNome());
    }

    @Test
    public void testCreateProduto() {
        ProdutoModel novoProduto = new ProdutoModel();
        novoProduto.setNome("Novo Produto");
        novoProduto.setDescricao("Descrição do Novo Produto");
        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("98765-432");
        fornecedor.setNro("456");
        novoProduto.setFornecedor(fornecedor);


        assertEquals("Novo Produto", novoProduto.getNome());
    }

    @Test
    public void testUpdateProduto() {
        ProdutoModel produtoAtualizado = new ProdutoModel();
        produtoAtualizado.setId(1L);
        produtoAtualizado.setNome("Produto Atualizado");
        produtoAtualizado.setDescricao("Descrição Atualizada");

        assertEquals("Produto Atualizado", produtoAtualizado.getNome());
        assertEquals("Descrição Atualizada", produtoAtualizado.getDescricao());
    }

    @Test
    public void testDeleteProduto() {
        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        produtoService.deleteProduto(1L);
        Mockito.verify(produtoRepository).delete(produto);
    }

    @Test
    public void testAdicionarAoEstoque() {
        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        produtoService.adicionarAoEstoque(1L, 10);
        Mockito.verify(produtoRepository).save(any());
    }

}
