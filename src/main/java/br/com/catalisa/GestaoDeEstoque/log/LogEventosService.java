package br.com.catalisa.GestaoDeEstoque.log;


import br.com.catalisa.GestaoDeEstoque.enums.TipoLogEvento;
import br.com.catalisa.GestaoDeEstoque.model.EstoqueModel;
import br.com.catalisa.GestaoDeEstoque.model.FornecedorModel;
import br.com.catalisa.GestaoDeEstoque.model.ProdutoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEventosService {

    private static final Logger logger = LoggerFactory.getLogger(LogEventosModel.class);

    @Autowired
    LogEventosRepository logEventosRepository;

    public void gerarLogListarAll(TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemLog = new LogEventosModel();

        switch (tipoLogEvento) {
            case LISTOU_FORNECEDORES -> {
                listagemLog.setEvento("[LOG] - Listou todos os Fornecedores");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            case LISTOU_PRODUTOS -> {
                listagemLog.setEvento("[LOG] - Listou todos os Produtos");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            case LISTOU_TODO_ESTOQUE -> {
                listagemLog.setEvento("[LOG] - Listou todos os estoques");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            default -> throw new IllegalArgumentException("Tipo de LogEventos não suportado: " + tipoLogEvento);
        }
    }

    public <T> void gerarLogBuscaDePeloId(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof FornecedorModel fornecedor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Fornecedor com ID: %d, nome: '%s', telefone: '%s', cep: '%s', número: '%s' foi encontrado.",
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getTelefone(),
                    fornecedor.getCep(),
                    fornecedor.getNro()));
        } else if (entidade instanceof ProdutoModel produto) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Produto com ID %d, código de barras: '%s', nome: '%s', marca: '%s', descrição: '%s' foi encontrado.",
                    produto.getId(),
                    produto.getCodigoBarras(),
                    produto.getNome(),
                    produto.getMarca(),
                    produto.getDescricao()
                    ));
        } else if (entidade instanceof EstoqueModel estoque) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Estoque com ID %d, Nome do Produto: '%s', Quantidade: '%d', custo do produto: '%s', valor de venda do produto: '%s' foi encontrada.",
                    estoque.getId(),
                    estoque.getProduto().getNome(),
                    estoque.getQuantidade(),
                    estoque.getValorCusto(),
                    estoque.getValorVenda()));
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }

    public <T> void gerarLogCadastroRealizado(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof FornecedorModel fornecedor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Fornecedor com ID: %d, nome: '%s', telefone: '%s', cep: '%s', número: '%s' foi cadastrado.",
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getTelefone(),
                    fornecedor.getCep(),
                    fornecedor.getNro()));
        } else if (entidade instanceof ProdutoModel produto) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Produto com ID %d, código de barras: '%s', nome: '%s', marca: '%s', descrição: '%s' foi cadastrado.",
                    produto.getId(),
                    produto.getCodigoBarras(),
                    produto.getNome(),
                    produto.getMarca(),
                    produto.getDescricao()
            ));
        } else if (entidade instanceof EstoqueModel estoque) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Estoque com ID %d, Nome do Produto: '%s', Quantidade: '%d', custo do produto: '%s', valor de venda do produto: '%s' foi cadastrado.",
                    estoque.getId(),
                    estoque.getProduto().getNome(),
                    estoque.getQuantidade(),
                    estoque.getValorCusto(),
                    estoque.getValorVenda()));
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }

    public <T> void gerarLogDeleteRealizado(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof FornecedorModel fornecedor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Fornecedor com ID: %d, nome: '%s', telefone: '%s', cep: '%s', número: '%s' foi deletado.",
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getTelefone(),
                    fornecedor.getCep(),
                    fornecedor.getNro()));
        } else if (entidade instanceof ProdutoModel produto) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Produto com ID %d, código de barras: '%s', nome: '%s', marca: '%s', descrição: '%s' foi deletado.",
                    produto.getId(),
                    produto.getCodigoBarras(),
                    produto.getNome(),
                    produto.getMarca(),
                    produto.getDescricao()
            ));
        } else if (entidade instanceof EstoqueModel estoque) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Estoque com ID %d, Nome do Produto: '%s', Quantidade: '%d', custo do produto: '%s', valor de venda do produto: '%s' foi deletado.",
                    estoque.getId(),
                    estoque.getProduto().getNome(),
                    estoque.getQuantidade(),
                    estoque.getValorCusto(),
                    estoque.getValorVenda()));
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }


    public <T> void gerarLogAtualizacaoRealizada(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof FornecedorModel fornecedor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Fornecedor com ID: %d, nome: '%s', telefone: '%s', cep: '%s', número: '%s' foi alterado.",
                    fornecedor.getId(),
                    fornecedor.getNome(),
                    fornecedor.getTelefone(),
                    fornecedor.getCep(),
                    fornecedor.getNro()));
        } else if (entidade instanceof ProdutoModel produto) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Produto com ID %d, código de barras: '%s', nome: '%s', marca: '%s', descrição: '%s' foi alterado.",
                    produto.getId(),
                    produto.getCodigoBarras(),
                    produto.getNome(),
                    produto.getMarca(),
                    produto.getDescricao()
            ));
        } else if (entidade instanceof EstoqueModel estoque) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Estoque com ID %d, Nome do Produto: '%s', Quantidade: '%d', custo do produto: '%s', valor de venda do produto: '%s' foi alterado.",
                    estoque.getId(),
                    estoque.getProduto().getNome(),
                    estoque.getQuantidade(),
                    estoque.getValorCusto(),
                    estoque.getValorVenda()));
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }



}
