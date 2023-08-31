package br.com.catalisa.GestaoDeEstoque.log;


import br.com.catalisa.GestaoDeEstoque.log.LogEventosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventosRepository extends JpaRepository<LogEventosModel, Long> {
}
