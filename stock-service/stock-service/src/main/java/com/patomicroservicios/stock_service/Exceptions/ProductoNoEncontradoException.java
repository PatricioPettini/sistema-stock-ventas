package com.patomicroservicios.stock_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(Long idProducto) {
        super("El producto" + idProducto +"no fue encontrado!");
    }
}
