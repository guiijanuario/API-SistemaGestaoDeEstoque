package br.com.catalisa.GestaoDeEstoque.exception;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class IdNaoEncontradoExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "ID não encontrado";
        IdNaoEncontradoException exception = new IdNaoEncontradoException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test(expected = IdNaoEncontradoException.class)
    public void testThrowIdNaoEncontratoException() {
        throw new IdNaoEncontradoException("ID não encontrado");
    }

    @Test(expected = NoSuchElementException.class)
    public void testThrowNoSuchElementException() {
        throw new IdNaoEncontradoException("ID não encontrado");
    }
}
