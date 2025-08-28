package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class MarcaNoEncontradaException extends RuntimeException{
    public MarcaNoEncontradaException(Long id) {
        super("La marca con id "+ id + " no fue encontrada!");
    }
}
