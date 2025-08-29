package com.patomicroservicios.productos_service.repository;

import com.patomicroservicios.productos_service.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria,Long> {
    Categoria getByDescripcion(String descripcion);
}
