package com.patomicroservicios.carrito_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO{
    private Long id;
    private Long idProducto;
    private int cantidad;
}