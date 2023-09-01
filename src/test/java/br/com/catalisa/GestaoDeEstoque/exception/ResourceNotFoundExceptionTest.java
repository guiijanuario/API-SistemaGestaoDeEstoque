package br.com.catalisa.GestaoDeEstoque.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Recurso não encontrado";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testThrowResourceNotFoundException() {
        throw new ResourceNotFoundException("Recurso não encontrado");
    }
}
