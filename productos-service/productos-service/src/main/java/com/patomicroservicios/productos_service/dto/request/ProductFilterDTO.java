package com.patomicroservicios.productos_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {
    private Long idMarca;
    private Long idTipoProducto;
    private Boolean estado;
}