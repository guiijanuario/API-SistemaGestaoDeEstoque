package br.com.catalisa.GestaoDeEstoque.Service;

import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;
import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.repository.EstoqueRepository;
import br.com.catalisa.GestaoDeEstoque.service.EstoqueService;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//    @Test
//    public void testListarEstoquePeloId() throws Exception {
//
//        List<EstoqueModel> estoqueList = new ArrayList<>();
//        CepModel cepMockEndereco = new CepModel();
//        cepMockEndereco.setId(1L);
//        cepMockEndereco.setCep("87045440");
//        cepMockEndereco.setLogradouro("Rua gaivota");
//        cepMockEndereco.setBairro("Jardim Olímpico");
//        cepMockEndereco.setLocalidade("Maringá");
//        cepMockEndereco.setUf("PR");
//
//        FornecedorModel fornecedor = new FornecedorModel();
//        fornecedor.setId(1L);
//        fornecedor.setNome("Fornecedor 1");
//        fornecedor.setTelefone("987654321");
//        fornecedor.setCep("87045440");
//        fornecedor.setNro("456");
//        fornecedor.setCepModel(cepMockEndereco);
//
//        ProdutoModel produto = new ProdutoModel();
//        produto.setId(1L);
//        produto.setCodigoBarras("123984411");
//        produto.setMarca("Billy Dog");
//        produto.setNome("Novo nome do produto");
//        produto.setDescricao("Nova descrição do produto");
//        produto.setCategoria(CategoriaProduto.ALIMENTOS);
//        produto.setFornecedor(fornecedor);
//
//        EasyRandom generator = new EasyRandom();
//        EstoqueModel estoque = generator.nextObject(EstoqueModel.class);
//
//        estoque.setProduto(produto);
//        estoque.getProduto().setId(1L);
//        estoqueList.add(estoque);
//
//        when(estoqueRepository.findByProdutoId(1L)).thenReturn(estoqueList);
//
//        List<EstoqueModel> result = estoqueService.listarTodoEstoque();
//        // ver o por que não está dando certo, e estudar mais a respeito, ver com mentor.
//        assertEquals(1, result.size());
//    }

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
