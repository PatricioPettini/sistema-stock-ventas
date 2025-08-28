package com.patomicroservicios.carrito_service.repository;

import com.patomicroservicios.carrito_service.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarritoRepository extends JpaRepository<Carrito,Long> {



}
