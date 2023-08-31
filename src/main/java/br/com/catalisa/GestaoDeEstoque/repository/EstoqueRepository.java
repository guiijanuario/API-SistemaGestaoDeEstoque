package br.com.catalisa.GestaoDeEstoque.repository;

import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueModel, Long> {
    List<EstoqueModel> findByProdutoId(Long produtoId);
}
