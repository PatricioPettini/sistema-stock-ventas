package com.patomicroservicios.carrito_service.repository;

import com.patomicroservicios.carrito_service.config.FeignSecurityConfig;
import com.patomicroservicios.carrito_service.dto.response.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "stock-service", configuration = FeignSecurityConfig.class)
public interface stockAPI {

    @GetMapping("/api/stock/get/{codigoProducto}")
    StockDTO getCantidadStockPoducto(@PathVariable Long codigoProducto);

    @PutMapping("/api/stock/put/{productId}/{nuevaCantidad}")
    void editStock(@PathVariable Long productId, @PathVariable int nuevaCantidad);

}
