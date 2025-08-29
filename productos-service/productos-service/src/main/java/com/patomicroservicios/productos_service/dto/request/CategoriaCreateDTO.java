package com.patomicroservicios.productos_service.dto.request;

import com.patomicroservicios.productos_service.dto.response.CategoriaDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaCreateDTO {
    @NotNull(message = "Description cannot be null")
    private String descripcion;
    private CategoriaDTO categoriaPadre;
}
