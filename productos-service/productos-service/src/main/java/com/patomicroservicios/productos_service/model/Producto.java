package com.patomicroservicios.productos_service.model;

import com.patomicroservicios.productos_service.Exceptions.ProductoActivoException;
import com.patomicroservicios.productos_service.Exceptions.ProductoInactivoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private String nombre;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Long idMarca;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Long idTipoProducto;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Double precioIndividual;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Boolean estado;


    public void activar(){
        if(this.estado==true) throw new ProductoActivoException();
        this.estado=true;
    }

    public void inactivar(){
        if(this.estado==false) throw new ProductoInactivoException();
        this.estado=false;
    }

}
