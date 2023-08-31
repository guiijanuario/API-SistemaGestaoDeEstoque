package br.com.catalisa.GestaoDeEstoque.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
