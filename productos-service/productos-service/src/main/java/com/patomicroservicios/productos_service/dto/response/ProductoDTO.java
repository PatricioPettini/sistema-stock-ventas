package com.patomicroservicios.productos_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long codigo;
    private String nombre;
    private MarcaDTO marca;
    private TipoProductoDTO tipo;
    private double precioIndividual;
    private Boolean estado;
}