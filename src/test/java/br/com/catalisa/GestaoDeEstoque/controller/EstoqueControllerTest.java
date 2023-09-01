package br.com.catalisa.GestaoDeEstoque.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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

//    @Test
//    public void testCriarEntradaEstoque() throws Exception {
//        EasyRandom generator = new EasyRandom();
//        EstoqueRequestDto request = new EstoqueRequestDto(
//                1L,
//                340,
//                new BigDecimal("50.0"),
//                new BigDecimal("120.0"),
//                "Lote123",
//                "2023-12-31"
//        );
//
//        ProdutoModel produtoMock = generator.nextObject(ProdutoModel.class);
//        produtoMock.setId(1L);
//
//
//        Mockito.when(produtoService.getProdutoById(1L)).thenReturn(Optional.of(produtoMock));
//
//        EstoqueModel estoqueCriadoMock = generator.nextObject(EstoqueModel.class);
//
//        //Mockito.when(estoqueService.criarEntradaEstoque(new ProdutoModel(),340,new BigDecimal("50.0"), new BigDecimal("120.0"),"Lote123"),"2023-12-31").thenReturn(estoqueCriadoMock);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/entrada")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "\t\"id\": 1,\n" +
//                                "\t\"produto\": {\n" +
//                                "\t\t\"id\": 1,\n" +
//                                "\t\t\"codigoBarras\": \"123984411\",\n" +
//                                "\t\t\"marca\": \"Billy Dog\",\n" +
//                                "\t\t\"nome\": \"Ração Billy Dog porte pequeno sabor frango\",\n" +
//                                "\t\t\"descricao\": \"Ração sabor carne para cachorros de porte médio\",\n" +
//                                "\t\t\"categoria\": \"ALIMENTOS\",\n" +
//                                "\t\t\"fornecedor\": {\n" +
//                                "\t\t\t\"id\": 1,\n" +
//                                "\t\t\t\"nome\": \"Pet Atacadista Numero 2\",\n" +
//                                "\t\t\t\"telefone\": \"(44) 998661234\",\n" +
//                                "\t\t\t\"cep\": \"04522-030\",\n" +
//                                "\t\t\t\"nro\": \"140\",\n" +
//                                "\t\t\t\"cepModel\": {\n" +
//                                "\t\t\t\t\"id\": 1,\n" +
//                                "\t\t\t\t\"cep\": \"04522-030\",\n" +
//                                "\t\t\t\t\"logradouro\": \"Rua Gaivota\",\n" +
//                                "\t\t\t\t\"bairro\": \"Moema\",\n" +
//                                "\t\t\t\t\"localidade\": \"São Paulo\",\n" +
//                                "\t\t\t\t\"uf\": \"SP\"\n" +
//                                "\t\t\t}\n" +
//                                "\t\t}\n" +
//                                "\t},\n" +
//                                "\t\"quantidade\": 340,\n" +
//                                "\t\"valorCusto\": 50.0,\n" +
//                                "\t\"valorVenda\": 120.0,\n" +
//                                "\t\"lote\": \"Lote123\",\n" +
//                                "\t\"validade\": \"2023-12-31\",\n" +
//                                "\t\"dataHoraInsercao\": \"2023-08-31T11:15:37.142754\",\n" +
//                                "\t\"dataHoraRetirada\": null,\n" +
//                                "\t\"quantidadeDaUltimaRetirada\": 0\n" +
//                                "}"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

}
