package com.patomicroservicios.productos_service.repository;

import com.patomicroservicios.productos_service.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca,Long> {
    Marca getByDescripcion(String descripcion);
}
