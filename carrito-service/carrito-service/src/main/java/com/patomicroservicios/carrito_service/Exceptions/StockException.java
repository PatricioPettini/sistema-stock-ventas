package com.patomicroservicios.carrito_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockException extends RuntimeException{
    public StockException() {
        super("No hay suficiente Stock!");
    }
}
