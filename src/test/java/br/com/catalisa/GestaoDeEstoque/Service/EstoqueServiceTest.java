package br.com.catalisa.GestaoDeEstoque.Service;

import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.repository.EstoqueRepository;
import br.com.catalisa.GestaoDeEstoque.service.EstoqueService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(2L);
        fornecedor.setNome("Fornecedor 2");
        fornecedor.setTelefone("0987876554");
        fornecedor.setCep("98765432");
        fornecedor.setNro("456");

        CepModel cepModel = new CepModel();
        cepModel.setId(1L);
        cepModel.setLogradouro("Av Pion Raul");
        cepModel.setBairro("Jardim América");
        cepModel.setCep("87045400");
        cepModel.setUf("PR");
        cepModel.setLocalidade("Maringá");
        fornecedor.setCepModel(cepModel);

        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");
        produto.setFornecedor(fornecedor);

        EstoqueModel estoque = new EstoqueModel();
        estoque.setProduto(produto);
    }

    @Test
    public void testListarTodoEstoque() {
        List<EstoqueModel> estoqueList = new ArrayList<>();
        when(estoqueRepository.findAll()).thenReturn(estoqueList);

        List<EstoqueModel> result = estoqueService.listarTodoEstoque();
        assertEquals(estoqueList, result);
    }

//    @Test
//    public void testCriarEntradaEstoque() {
//        FornecedorModel fornecedor = new FornecedorModel();
//        fornecedor.setId(2L);
//        fornecedor.setNome("Fornecedor 2");
//        fornecedor.setTelefone("0987876554");
//        fornecedor.setCep("98765432");
//        fornecedor.setNro("456");
//
//        CepModel cepModel = new CepModel();
//        cepModel.setId(1L);
//        cepModel.setLogradouro("Av Pion Raul");
//        cepModel.setBairro("Jardim América");
//        cepModel.setCep("87045400");
//        cepModel.setUf("PR");
//        cepModel.setLocalidade("Maringá");
//        fornecedor.setCepModel(cepModel);
//
//        ProdutoModel produto = new ProdutoModel();
//        produto.setId(1L);
//        produto.setNome("Produto Teste");
//        produto.setDescricao("Descrição do Produto Teste");
//        produto.setFornecedor(fornecedor);
//
//        EstoqueModel estoque = new EstoqueModel();
//        estoque.setProduto(produto);
//        estoque.setValorCusto(new BigDecimal("100.00"));
//        estoque.setValorVenda(new BigDecimal("150.00"));
//        estoque.setLote("12345");
//        estoque.setValidade(LocalDate.now());
//
//        int quantidade = 10;
//        BigDecimal valorCusto = new BigDecimal("100.00");
//        BigDecimal valorVenda = new BigDecimal("150.00");
//        String lote = "12345";
//        LocalDate validade = LocalDate.now();
//
//        EstoqueModel result = estoqueService.criarEntradaEstoque(produto, quantidade, valorCusto, valorVenda, lote, validade);
//
//        when(estoqueRepository.save(estoque)).thenReturn(estoque);
//        Mockito.verify(estoqueRepository).save(estoque);
//
//        assertEquals(estoque, result);
//    }
}
