package com.patomicroservicios.stock_service.repository;

import com.patomicroservicios.stock_service.dto.response.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productos-service")
public interface productoAPI {

    @GetMapping("/api/producto/get/{codigoProducto}")
    public ProductoDTO getProducto(@PathVariable Long codigoProducto);
}
