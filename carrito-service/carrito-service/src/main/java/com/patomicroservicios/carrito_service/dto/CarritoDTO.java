package com.patomicroservicios.carrito_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDTO {
    private Long id;
    private double totalPrice;
    private List<ProductoDTO> productList;
}
