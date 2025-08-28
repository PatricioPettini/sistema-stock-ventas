package com.patomicroservicios.productos_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.patomicroservicios.productos_service.Exceptions.ProductoActivoException;
import com.patomicroservicios.productos_service.Exceptions.ProductoInactivoException;
import com.patomicroservicios.productos_service.dto.response.TipoProductoDTO;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_marca", nullable = false,
            foreignKey = @ForeignKey(name = "fk_producto_marca"))
    private Marca marca;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipoProducto", nullable = false,
            foreignKey = @ForeignKey(name = "fk_producto_tipo"))
    private TipoProducto tipoProducto;
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
