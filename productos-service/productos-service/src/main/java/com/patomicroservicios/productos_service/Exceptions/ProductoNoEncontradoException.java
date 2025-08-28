package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException() {
        super("El producto no fue encontrado!");
    }
}
