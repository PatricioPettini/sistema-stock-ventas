package com.patomicroservicios.stock_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Long idProducto;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private int cantidad;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaActualizacion;
}
