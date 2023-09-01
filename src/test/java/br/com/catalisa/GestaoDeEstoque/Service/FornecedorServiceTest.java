package br.com.catalisa.GestaoDeEstoque.Service;

import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.repository.FornecedorRepository;
import br.com.catalisa.GestaoDeEstoque.service.CepService;
import br.com.catalisa.GestaoDeEstoque.service.FornecedorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class FornecedorServiceTest {
    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private CepService cepService;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("87045400");
        fornecedor.setNro("456");

        CepModel cepModel = new CepModel();
        cepModel.setId(1L);
        cepModel.setLogradouro("Av Pion Raul");
        cepModel.setBairro("Jardim América");
        cepModel.setCep("87045400");
        cepModel.setUf("PR");
        cepModel.setLocalidade("Maringá");
    }

    @Test
    public void testGetAllFornecedores() {
        List<FornecedorModel> fornecedores = new ArrayList<>();
        FornecedorModel fornecedor = new FornecedorModel();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor 1");
        fornecedor.setTelefone("987654321");
        fornecedor.setCep("87045400");
        fornecedor.setNro("456");

        FornecedorModel fornecedor2 = new FornecedorModel();
        fornecedor2.setId(2L);
        fornecedor2.setNome("Fornecedor 2");
        fornecedor2.setTelefone("0987876554");
        fornecedor2.setCep("98765432");
        fornecedor2.setNro("456");
        fornecedores.add(fornecedor);
        fornecedores.add(fornecedor2);

        CepModel cepModel = new CepModel();
        cepModel.setId(1L);
        cepModel.setLogradouro("Av Pion Raul");
        cepModel.setBairro("Jardim América");
        cepModel.setCep("87045400");
        cepModel.setUf("PR");
        cepModel.setLocalidade("Maringá");

        fornecedor.setCepModel(cepModel);
        fornecedor2.setCepModel(cepModel);

        when(fornecedorRepository.findAll()).thenReturn(fornecedores);

        List<FornecedorModel> result = fornecedorService.getAllFornecedores();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetFornecedorById() {
        Long fornecedorId = 1L;
        FornecedorModel fornecedor = new FornecedorModel();
        when(fornecedorRepository.findById(fornecedorId)).thenReturn(Optional.of(fornecedor));

        Optional<FornecedorModel> result = fornecedorService.getFornecedorById(fornecedorId);
        assertEquals(fornecedor, result.orElse(null));
    }

//    @Test
//    public void testCreateFornecedor() {
//        String cep = "87045650";
//        CepModel cepModel = new CepModel();
//        when(cepService.findCep(cep)).thenReturn(cepModel);
//
//        FornecedorModel fornecedor = new FornecedorModel();
//        fornecedor.setCep(cep);
//
//        FornecedorModel result = fornecedorService.createFornecedor(fornecedor);
//
//        Mockito.verify(fornecedorRepository).save(fornecedor);
//
//        assertEquals(cepModel, result.getCepModel());
//    }


//    @Test
//    public void testUpdateFornecedor() {
//        Long fornecedorId = 1L;
//        FornecedorModel fornecedorExistente = new FornecedorModel();
//        when(fornecedorRepository.findById(fornecedorId)).thenReturn(Optional.of(fornecedorExistente));
//
//        String cep = "87045650";
//        CepModel cepModel = new CepModel();
//        when(cepService.findCep(cep)).thenReturn(cepModel);
//
//        FornecedorModel fornecedorAtualizado = new FornecedorModel();
//
//        FornecedorModel result = fornecedorService.updateFornecedor(fornecedorId, fornecedorAtualizado);
//
//        Mockito.verify(fornecedorRepository).save(fornecedorExistente);
//        assertEquals(cepModel, result.getCepModel());
//    }

    @Test
    public void testDeleteFornecedor() {
        Long fornecedorId = 1L;
        FornecedorModel fornecedor = new FornecedorModel();
        when(fornecedorRepository.findById(fornecedorId)).thenReturn(Optional.of(fornecedor));

        fornecedorService.deleteFornecedor(fornecedorId);

        Mockito.verify(fornecedorRepository).delete(fornecedor);
    }

}
