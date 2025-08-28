package com.patomicroservicios.stock_service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private Long id;
    private Long idProducto;
    private int cantidad;
    private LocalDate fechaActualizacion;
}
