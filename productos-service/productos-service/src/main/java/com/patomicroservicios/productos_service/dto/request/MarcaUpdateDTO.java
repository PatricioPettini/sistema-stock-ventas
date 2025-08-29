package com.patomicroservicios.productos_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarcaUpdateDTO {
    @NotNull(message = "Description cannot be null")
    private String descripcion;
}