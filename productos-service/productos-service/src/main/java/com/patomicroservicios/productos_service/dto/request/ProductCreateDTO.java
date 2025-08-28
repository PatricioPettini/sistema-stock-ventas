package com.patomicroservicios.productos_service.dto.request;

import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.dto.response.TipoProductoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String nombre;

    @NotNull(message = "Brand ID cannot be null")
    private Long idMarca;

    @NotNull(message = "Product type ID cannot be null")
    private Long idTipoProducto;

    @Positive(message = "Price must be greater than zero")
    private double precioIndividual;

    @NotBlank(message = "Status cannot be blank")
    private Boolean estado;
}