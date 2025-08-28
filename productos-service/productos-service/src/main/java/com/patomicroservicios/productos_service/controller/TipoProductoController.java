package com.patomicroservicios.productos_service.controller;

import com.patomicroservicios.productos_service.dto.response.TipoProductoDTO;
import com.patomicroservicios.productos_service.model.TipoProducto;
import com.patomicroservicios.productos_service.service.interfaces.ITipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo")
public class TipoProductoController {

    @Autowired
    ITipoProductoService tipoProductoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<TipoProductoDTO> getTipoProducto(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProductoService.getTipoProductoDTO(id));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<TipoProducto>> getAll() {
        List<TipoProducto> tipos = tipoProductoService.getAll();
        return ResponseEntity.ok(tipos);
    }

    @PostMapping("/post")
    public ResponseEntity<TipoProductoDTO> addProduct(@RequestBody TipoProducto tipo) {
        TipoProductoDTO tip=tipoProductoService.add(tipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(tip);

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<TipoProductoDTO> putProduct(@PathVariable Long id, @RequestBody TipoProducto tipo) {
        TipoProductoDTO tip=tipoProductoService.update(id, tipo);
        return ResponseEntity.ok(tip);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTipoProducto(@PathVariable Long id) {
        tipoProductoService.delete(id);
        return ResponseEntity.ok("Tipo de producto eliminado correctamente");
    }

}
