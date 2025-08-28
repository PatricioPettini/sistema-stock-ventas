package com.patomicroservicios.stock_service.repository;

import com.patomicroservicios.stock_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockRepository extends JpaRepository<Stock,Long> {

    @Query("SELECT s FROM Stock s WHERE s.idProducto = :idProducto")
    public Stock getProductStock(Long idProducto);
}
