package br.com.catalisa.GestaoDeEstoque.repository;

import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    List<ProdutoModel> findByNome(String nome);
}
