package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class ProductoInactivoException extends RuntimeException{
    public ProductoInactivoException(Long id) {
        super("El producto" + id + "ya se encuentra inactivo!");
    }
}
