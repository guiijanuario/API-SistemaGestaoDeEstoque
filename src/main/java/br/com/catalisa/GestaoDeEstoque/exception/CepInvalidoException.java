package br.com.catalisa.GestaoDeEstoque.exception;

public class CepInvalidoException extends RuntimeException{
    public CepInvalidoException(String mensagem) {
        super(mensagem);
    }
}
