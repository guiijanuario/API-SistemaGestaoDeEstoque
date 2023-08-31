package br.com.catalisa.GestaoDeEstoque.repository;

import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CepRepository extends JpaRepository<CepModel,Long> {
}
