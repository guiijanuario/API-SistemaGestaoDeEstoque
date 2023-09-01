package br.com.catalisa.GestaoDeEstoque.service;

import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.repository.CepRepository;
import br.com.catalisa.GestaoDeEstoque.repository.FornecedorRepository;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private CepService cepService;

    @Autowired
    private CepValidations cepValidations;

    public List<FornecedorModel> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Optional<FornecedorModel> getFornecedorById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public FornecedorModel createFornecedor(FornecedorModel fornecedor) {
        if (fornecedor.getCep() != null) {
            CepModel cepModel = cepService.findCep(fornecedor.getCep());
            fornecedor.setCepModel(cepModel);
        } else throw new ResourceNotFoundException("O CEP deve estar no formato '87045650' ou '87045-650'");
        return fornecedorRepository.save(fornecedor);
    }

    public FornecedorModel updateFornecedor(Long id, FornecedorModel fornecedorAtualizado) {
        FornecedorModel fornecedorExistente = getFornecedorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));

        fornecedorExistente.setNome(fornecedorAtualizado.getNome());
        fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
        fornecedorExistente.setNro(fornecedorAtualizado.getNro());
        fornecedorExistente.setCepModel(cepService.findCep(String.valueOf(fornecedorAtualizado.getCepModel())));

        return fornecedorRepository.save(fornecedorExistente);
    }

    public void deleteFornecedor(Long id) {
        FornecedorModel fornecedor = getFornecedorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));

        fornecedorRepository.delete(fornecedor);
    }

}
