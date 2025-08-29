package com.patomicroservicios.productos_service.model;

import com.patomicroservicios.productos_service.Exceptions.ProductoActivoException;
import com.patomicroservicios.productos_service.Exceptions.ProductoInactivoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "id_categoria", nullable = false,
            foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Double precioIndividual;
    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Boolean estado;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    private LocalDateTime actualizadoEn;



    public void activar(){
        if(this.estado==true) throw new ProductoActivoException(this.codigo);
        this.estado=true;
    }

    public void inactivar(){
        if(this.estado==false) throw new ProductoInactivoException(this.codigo);
        this.estado=false;
    }

}
