package com.patomicroservicios.stock_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockPatch {
    @NotNull
    private Long idProducto;
    @NotNull
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;
}
