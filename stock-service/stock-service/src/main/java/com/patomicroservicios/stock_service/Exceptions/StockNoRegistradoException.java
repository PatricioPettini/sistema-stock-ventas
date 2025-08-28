package com.patomicroservicios.stock_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNoRegistradoException extends RuntimeException{
    public StockNoRegistradoException() {
        super("El stock de ese producto no està registrado!");
    }
}
