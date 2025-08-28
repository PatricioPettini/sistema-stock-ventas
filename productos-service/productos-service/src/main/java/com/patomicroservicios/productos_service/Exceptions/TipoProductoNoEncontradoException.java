package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TipoProductoNoEncontradoException extends RuntimeException{
    public TipoProductoNoEncontradoException(Long id) {
        super("El tipo de producto con id "+ id + " no fue encontrado!");
    }
}