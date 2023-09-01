package br.com.catalisa.GestaoDeEstoque.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViaCepNullExceptionTest {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Valor nulo para ViaCep";
        ViaCepNullException exception = new ViaCepNullException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test(expected = ViaCepNullException.class)
    public void testThrowViaCepNullException() {
        throw new ViaCepNullException("Valor nulo para ViaCep");
    }

}
