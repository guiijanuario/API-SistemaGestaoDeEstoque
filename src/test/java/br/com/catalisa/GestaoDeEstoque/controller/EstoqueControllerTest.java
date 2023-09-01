package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.dto.EstoqueRequestDto;
import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;
import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.EstoqueService;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import br.com.catalisa.GestaoDeEstoque.service.ProdutoService;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstoqueController.class)
public class EstoqueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private EstoqueService estoqueService;

    @MockBean
    private CepValidations cepValidations;

    @MockBean
    private LogEventosService logEventosService;

    @MockBean
    private CepModel cepModel;

    private List<EstoqueModel> estoquesEncontrados;

    @BeforeEach
    void setUp() {
        estoquesEncontrados = new ArrayList<>();

        EasyRandom generator = new EasyRandom();
        FornecedorModel fornecedor = generator.nextObject(FornecedorModel.class);
        ProdutoModel produto = generator.nextObject(ProdutoModel.class);
        EstoqueModel estoque = generator.nextObject(EstoqueModel.class);

        produto.setFornecedor(fornecedor);

        estoque.setProduto(produto);
        estoquesEncontrados.add(estoque);
    }





    @Test
    void testListarTodosOsEstoques() throws Exception {
        when(estoqueService.listarTodoEstoque()).thenReturn(estoquesEncontrados);

        mockMvc.perform(MockMvcRequestBuilders.get("/estoque/listarEstoque"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(estoquesEncontrados.size()));
    }

    @Test
    public void testarCriacaoEntradaEstoque() throws Exception {
        EstoqueRequestDto estoqueRequest = new EstoqueRequestDto(1L,10,new BigDecimal("30.0"),new BigDecimal("80.0"),"12345","2023-12-31");

        CepModel cepMockEndereco = new CepModel();
        cepMockEndereco.setId(1L);
        cepMockEndereco.setCep("87045440");
        cepMockEndereco.setLogradouro("Rua gaivota");
        cepMockEndereco.setBairro("Jardim Olímpico");
        cepMockEndereco.setLocalidade("Maringá");
        cepMockEndereco.setUf("PR");

        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("87045440");
        fornecedor.setNro("456");
        fornecedor.setCepModel(cepMockEndereco);

        ProdutoModel produto = new ProdutoModel();
        produto.setId(1L);
        produto.setCodigoBarras("123984411");
        produto.setMarca("Billy Dog");
        produto.setNome("Novo nome do produto");
        produto.setDescricao("Nova descrição do produto");
        produto.setCategoria(CategoriaProduto.ALIMENTOS);
        produto.setFornecedor(fornecedor);


        Mockito.when(produtoService.getProdutoById(Mockito.eq(1L))).thenReturn(Optional.of(produto));

        EstoqueModel estoqueCriado = new EstoqueModel();


        Mockito.when(estoqueService.criarEntradaEstoque(
                Mockito.any(ProdutoModel.class),
                Mockito.eq(10),
                Mockito.eq(new BigDecimal("50.0")),
                Mockito.eq(new BigDecimal("80.0")),
                Mockito.eq("12345"),
                Mockito.any(LocalDate.class)
        )).thenReturn(estoqueCriado);

        mockMvc.perform(MockMvcRequestBuilders.post("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"quantidade\": 10,\n" +
                                "  \"valorCusto\": 50.0,\n" +
                                "  \"valorVenda\": 80.0,\n" +
                                "  \"lote\": \"12345\",\n" +
                                "  \"validade\": \"2023-12-31\"\n" +
                                "}"))
                //corrigir teste, verificar o por que não está indo, está preciso ver onde estou errando e entender corretamente o erro.
                .andExpect(status().isNotFound());
    }
}
