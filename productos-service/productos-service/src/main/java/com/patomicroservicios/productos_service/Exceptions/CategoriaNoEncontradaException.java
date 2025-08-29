package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNoEncontradaException extends RuntimeException{
    public CategoriaNoEncontradaException(Long id) {
        super("La categoria con id "+ id + " no fue encontrado!");
    }
}