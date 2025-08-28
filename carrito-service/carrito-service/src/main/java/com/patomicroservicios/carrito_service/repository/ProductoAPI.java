package com.patomicroservicios.carrito_service.repository;

import com.patomicroservicios.carrito_service.config.FeignSecurityConfig;
import com.patomicroservicios.carrito_service.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "productos-service", configuration = FeignSecurityConfig.class)
public interface ProductoAPI {

    @GetMapping("/api/producto/get/{codigoProducto}")
    public ProductoDTO getProducto(@PathVariable Long codigoProducto);

    @PostMapping("/api/producto/get/by-ids")
    List<ProductoDTO> getProductosByIdList(@RequestBody List<Long> ids);

}
