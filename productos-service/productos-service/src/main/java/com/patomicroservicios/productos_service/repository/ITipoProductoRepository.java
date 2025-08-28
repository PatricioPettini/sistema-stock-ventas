package com.patomicroservicios.productos_service.repository;

import com.patomicroservicios.productos_service.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoProductoRepository extends JpaRepository<TipoProducto,Long> {
}
