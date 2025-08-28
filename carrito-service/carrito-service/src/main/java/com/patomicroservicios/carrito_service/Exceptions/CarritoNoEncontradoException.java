package com.patomicroservicios.carrito_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarritoNoEncontradoException extends RuntimeException{
    public CarritoNoEncontradoException() {
        super("El carrito no fue encontrado!");
    }
}
