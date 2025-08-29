package com.patomicroservicios.carrito_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductoInactivoException extends RuntimeException{
    public ProductoInactivoException(Long idProducto) {
        super("El producto " + idProducto +" no esta activo!");
    }
}
