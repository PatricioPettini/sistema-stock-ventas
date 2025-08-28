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
public class StockCreateDTO {
        @NotNull
        private Long idProducto;
        @NotNull @Min(0) private Integer cantidad;
}
