package com.patomicroservicios.productos_service.controller;

import com.patomicroservicios.productos_service.dto.request.CategoriaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.CategoriaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.CategoriaDTO;
import com.patomicroservicios.productos_service.model.Categoria;
import com.patomicroservicios.productos_service.service.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    ICategoriaService categoriaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaProducto(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.getCategoriaDTO(id));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Categoria>> getAll() {
        List<Categoria> categorias = categoriaService.getAll();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/post")
    public ResponseEntity<CategoriaDTO> addProduct(@RequestBody CategoriaCreateDTO categoria) {
        CategoriaDTO tip=categoriaService.add(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(tip);

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<CategoriaDTO> putProduct(@PathVariable Long id, @RequestBody CategoriaUpdateDTO categoria) {
        CategoriaDTO tip=categoriaService.update(id, categoria);
        return ResponseEntity.ok(tip);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategoriaProducto(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok("Categoria de producto eliminado correctamente");
    }

}
