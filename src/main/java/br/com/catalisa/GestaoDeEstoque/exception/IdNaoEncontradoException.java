package br.com.catalisa.GestaoDeEstoque.exception;

import java.util.NoSuchElementException;

public class IdNaoEncontradoException extends NoSuchElementException {
    public IdNaoEncontradoException(String message) {
        super(message);
    }
}
