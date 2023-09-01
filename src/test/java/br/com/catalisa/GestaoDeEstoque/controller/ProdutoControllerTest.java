package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;
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
    public void testarCadastroDeProduto() throws Exception {
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
        produto.setCodigoBarras("123984411");
        produto.setMarca("Billy Dog");
        produto.setNome("Billy Dog");
        produto.setDescricao("Ração sabor carne para cachorros de porte médio");
        produto.setCategoria(CategoriaProduto.ALIMENTOS);
        produto.setFornecedor(fornecedor);

        Mockito.when(produtoService.createProduto(Mockito.any(produto.getClass()))).thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"codigoBarras\": \"123984411\",\n" +
                                "  \"marca\": \"Billy Dog\",\n" +
                                "  \"nome\": \"Ração Billy Dog porte pequeno sabor frango\",\n" +
                                "  \"descricao\": \"Ração sabor carne para cachorros de porte médio\",\n" +
                                "  \"categoria\": \"ALIMENTOS\",\n" +
                                "  \"fornecedor\": {\n" +
                                "    \"id\": 4\n" +
                                "  }\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigoBarras").value("123984411"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca").value("Billy Dog"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Billy Dog"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Ração sabor carne para cachorros de porte médio"))
                .andDo(print());
    }

    @Test
    public void testarAlteracaoDeProduto() throws Exception {
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
        produto.setCodigoBarras("123984411");
        produto.setMarca("Billy Dog");
        produto.setNome("Novo nome do produto");
        produto.setDescricao("Nova descrição do produto");
        produto.setCategoria(CategoriaProduto.ALIMENTOS);
        produto.setFornecedor(fornecedor);

        Mockito.when(produtoService.updateProduto(Mockito.eq(1L), Mockito.any(produto.getClass()))).thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"nome\": \"Novo Produto New\",\n" +
                                "  \"descricao\": \"New Descrição\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Novo nome do produto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Nova descrição do produto"))
                .andDo(print());
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