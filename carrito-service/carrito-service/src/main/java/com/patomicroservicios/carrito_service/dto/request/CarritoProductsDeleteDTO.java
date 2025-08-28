package com.patomicroservicios.carrito_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoProductsDeleteDTO {
    private Long idCarrito;
    private Long idProducto;
}
