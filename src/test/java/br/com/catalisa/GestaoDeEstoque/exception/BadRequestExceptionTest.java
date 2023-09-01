package br.com.catalisa.GestaoDeEstoque.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BadRequestExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Requisição inválida";
        BadRequestException exception = new BadRequestException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test(expected = BadRequestException.class)
    public void testThrowBadRequestException() {
        throw new BadRequestException("Requisição inválida");
    }
}
