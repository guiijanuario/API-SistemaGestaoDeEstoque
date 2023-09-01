package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private CepValidations cepValidations;

    @MockBean
    private LogEventosService logEventosService;

    @MockBean
    private CepModel cepModel;

    private List<ProdutoModel> produtosEncontrados;


    @BeforeEach
    void setUp() {
        produtosEncontrados = new ArrayList<>();

        EasyRandom generator = new EasyRandom();
        FornecedorModel fornecedor = generator.nextObject(FornecedorModel.class);
        ProdutoModel produto1 = generator.nextObject(ProdutoModel.class);
        ProdutoModel produto2 = generator.nextObject(ProdutoModel.class);

        fornecedor.setCep("12345-678");
        fornecedor.setNro("123");
        produto1.setFornecedor(fornecedor);
        produto2.setFornecedor(fornecedor);
        produtosEncontrados.add(produto1);
        produtosEncontrados.add(produto2);
    }

    @Test
    void testListarTodosProdutos() throws Exception {
        when(produtoService.getAllProdutos()).thenReturn(produtosEncontrados);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(produtosEncontrados.size()));
    }


    @Test
    void testListarProdutoPorId() throws Exception {
        Long produtoId = 1L;
        EasyRandom generator = new EasyRandom();
        CepModel cepMockEndereco = generator.nextObject(CepModel.class);
        FornecedorModel fornecedor = generator.nextObject(FornecedorModel.class);
        fornecedor.setCepModel(cepMockEndereco);
        ProdutoModel produto = generator.nextObject(ProdutoModel.class);

        when(produtoService.getProdutoById(produtoId)).thenReturn(Optional.of(produto));

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(produto.getNome()));
    }
    @Test
    public void testDeleteProduto() throws Exception {
        Long produtoId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/" + produtoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(produtoService).deleteProduto(produtoId);
    }

}