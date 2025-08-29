package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoriaRegistradaException extends RuntimeException{
    public CategoriaRegistradaException(String nombre) {
        super("La categoria "+ nombre + " ya esta registrada!");
    }
}
