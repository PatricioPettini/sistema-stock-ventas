package com.patomicroservicios.carrito_service.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private String idUser;
    private Double totalPrice;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ProductoCantidad> productList;
}