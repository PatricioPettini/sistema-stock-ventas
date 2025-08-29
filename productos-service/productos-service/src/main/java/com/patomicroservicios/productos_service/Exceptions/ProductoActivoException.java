package com.patomicroservicios.productos_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class ProductoActivoException extends RuntimeException{
    public ProductoActivoException(Long id) {
        super("El producto"+ id +"ya se encuentra activo!");
    }
}
