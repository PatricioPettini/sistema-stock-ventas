package com.patomicroservicios.productos_service.dto.request;

// package com.tuapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductPatchDTO {
    private String nombre;          // opcional
    private Long idMarca;           // opcional
    private Long idTipoProducto;    // opcional
    private Double precioIndividual;// opcional
    private Boolean estado;          // opcional (ej: "ACTIVO" / "INACTIVO")
}
