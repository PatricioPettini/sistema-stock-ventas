package com.patomicroservicios.carrito_service.repository;

import com.patomicroservicios.carrito_service.config.FeignSecurityConfig;
import com.patomicroservicios.carrito_service.dto.ProductoDTO;
import com.patomicroservicios.carrito_service.dto.response.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "productos-service", configuration = FeignSecurityConfig.class)
public interface productoAPI {

    @GetMapping("/api/producto/get/{codigoProducto}")
    ProductoDTO getProducto(@PathVariable Long codigoProducto);

}
