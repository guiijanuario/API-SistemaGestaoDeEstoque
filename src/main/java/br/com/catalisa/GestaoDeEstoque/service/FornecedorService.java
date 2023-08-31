package br.com.catalisa.GestaoDeEstoque.service;

import br.com.catalisa.GestaoDeEstoque.exception.ResourceNotFoundException;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<FornecedorModel> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Optional<FornecedorModel> getFornecedorById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public FornecedorModel createFornecedor(FornecedorModel fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public FornecedorModel updateFornecedor(Long id, FornecedorModel fornecedorAtualizado) {
        FornecedorModel fornecedorExistente = getFornecedorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));

        fornecedorExistente.setNome(fornecedorAtualizado.getNome());
        fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
        fornecedorExistente.setLogradouro(fornecedorAtualizado.getLogradouro());
        fornecedorExistente.setBairro(fornecedorAtualizado.getBairro());
        fornecedorExistente.setNroEnd(fornecedorAtualizado.getNroEnd());
        fornecedorExistente.setCep(fornecedorAtualizado.getCep());
        fornecedorExistente.setCidade(fornecedorAtualizado.getCidade());
        fornecedorExistente.setEstado(fornecedorAtualizado.getEstado());

        return fornecedorRepository.save(fornecedorExistente);
    }

    public void deleteFornecedor(Long id) {
        FornecedorModel fornecedor = getFornecedorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado com ID: " + id));

        fornecedorRepository.delete(fornecedor);
    }

}
