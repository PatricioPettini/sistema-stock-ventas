package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MarcaRegistradaException extends RuntimeException{
    public MarcaRegistradaException(String nombre) {
        super("La marca "+ nombre + " ya esta registrada!");
    }
}
