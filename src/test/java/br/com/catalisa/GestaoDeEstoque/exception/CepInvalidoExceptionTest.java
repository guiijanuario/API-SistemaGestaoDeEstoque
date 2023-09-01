package br.com.catalisa.GestaoDeEstoque.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CepInvalidoExceptionTest {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "CEP inválido";
        CepInvalidoException exception = new CepInvalidoException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test(expected = CepInvalidoException.class)
    public void testThrowCepInvalidoException() {
        throw new CepInvalidoException("CEP inválido");
    }
}
