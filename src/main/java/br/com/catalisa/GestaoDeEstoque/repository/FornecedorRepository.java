package br.com.catalisa.GestaoDeEstoque.repository;

import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorModel, Long> {
}
