package br.com.catalisa.GestaoDeEstoque.controller;

import br.com.catalisa.GestaoDeEstoque.enums.CategoriaProduto;
import br.com.catalisa.GestaoDeEstoque.log.LogEventosService;
import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    @MockBean
    private CepValidations cepValidations;

    @MockBean
    private LogEventosService logEventosService;

    @MockBean
    private CepModel cepModel;

    private List<FornecedorModel> fornecedorEncontrados;

    @BeforeEach
    void setUp() {
        fornecedorEncontrados = new ArrayList<>();
        FornecedorModel fornecedor1 = new FornecedorModel();
        FornecedorModel fornecedor2 = new FornecedorModel();

        fornecedor1.setId(1L);
        fornecedor1.setNome("Fornecedor 1");
        fornecedor1.setTelefone("123456789");
        fornecedor1.setCep("12345-678");
        fornecedor1.setNro("123");

        fornecedor2.setId(2L);
        fornecedor2.setNome("Fornecedor 2");
        fornecedor2.setTelefone("987654321");
        fornecedor2.setCep("98765-432");
        fornecedor2.setNro("456");
    }
    @Test
    void testListarTodosFornecedores() throws Exception {
        when(fornecedorService.getAllFornecedores()).thenReturn(fornecedorEncontrados);

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedores"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(fornecedorEncontrados.size()));
    }

    @Test
    public void testarCadastroDeFornecedor() throws Exception {
        CepModel cepMockEndereco = new CepModel();
        cepMockEndereco.setId(1L);
        cepMockEndereco.setCep("87045440");
        cepMockEndereco.setLogradouro("Rua gaivota");
        cepMockEndereco.setBairro("Jardim Olímpico");
        cepMockEndereco.setLocalidade("Maringá");
        cepMockEndereco.setUf("PR");

        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Pet Atacadista11111");
        fornecedor.setTelefone("(44) 998661234");
        fornecedor.setCep("87045442");
        fornecedor.setNro("140");
        fornecedor.setCepModel(cepMockEndereco);

        Mockito.when(fornecedorService.createFornecedor(Mockito.any(fornecedor.getClass()))).thenReturn(fornecedor);

        mockMvc.perform(MockMvcRequestBuilders.post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"nome\": \"Pet Atacadista11111\",\n" +
                                "  \"telefone\": \"(44) 998661234\",\n" +
                                "\t\"nro\": \"140\",\n" +
                                "  \"cep\": \"87045442\"\n" +
                                "}\n"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Pet Atacadista11111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefone").value("(44) 998661234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nro").value("140"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("87045442"))
                .andDo(print());
    }

    @Test
    void testListarFornecedorPorId() throws Exception {
        Long fornecedorId = 1L;
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

        when(fornecedorService.getFornecedorById(fornecedorId)).thenReturn(Optional.of(fornecedor));

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedores/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefone").value(fornecedor.getTelefone()));
    }

//    @Test
//    public void testarAlteracaoDeFornecedores() throws Exception {
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
//        Mockito.when(fornecedorService.updateFornecedor(Mockito.eq(1L), Mockito.any(fornecedor.getClass()))).thenReturn(fornecedor);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/fornecedores/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "  \"nome\": \"Novo Produto New\",\n" +
//                                "  \"descricao\": \"New Descrição\"\n" +
//                                "}"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Novo nome do produto"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Nova descrição do produto"))
//                .andDo(print());
//    }

    @Test
    void testaSedeletaFornecedor() throws Exception {
        Long fornecedorId = 1L;
        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("98765-432");
        fornecedor.setNro("456");

        when(fornecedorService.getFornecedorById(fornecedorId)).thenReturn(Optional.of(fornecedor));

        mockMvc.perform(MockMvcRequestBuilders.delete("/fornecedores/1"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(fornecedorService).deleteFornecedor(fornecedorId);
    }

}
