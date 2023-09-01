package br.com.catalisa.GestaoDeEstoque.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViaCepFormatExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Formato inválido para ViaCep";
        ViaCepFormatException exception = new ViaCepFormatException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test(expected = ViaCepFormatException.class)
    public void testThrowViaCepFormatException() {
        throw new ViaCepFormatException("Formato inválido para ViaCep");
    }
}
