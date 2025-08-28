package com.patomicroservicios.user_service.repository;

import com.patomicroservicios.user_service.config.FeignSecurityConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "carrito-service", configuration = FeignSecurityConfig.class)
public interface carritoAPI {

    @PostMapping("/api/carrito/{idUser}")
    void enviaridUser(@PathVariable String idUser);

}
