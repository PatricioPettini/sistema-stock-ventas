package com.patomicroservicios.productos_service.controller;

import com.patomicroservicios.productos_service.dto.request.MarcaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.MarcaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.model.Marca;
import com.patomicroservicios.productos_service.service.interfaces.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marca")
public class MarcaController {

    @Autowired
    IMarcaService marcaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<MarcaDTO> getMarca(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.getMarcaDTO(id));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Marca>> getAll() {
        List<Marca> marcas = marcaService.getAll();
        return ResponseEntity.ok(marcas);
    }

    @PostMapping("/post")
    public ResponseEntity<MarcaDTO> addProduct(@RequestBody MarcaCreateDTO marca) {
        MarcaDTO mar=marcaService.addMarca(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(mar);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<MarcaDTO> putProduct(@PathVariable Long id, @RequestBody MarcaUpdateDTO marca) {
        MarcaDTO mar=marcaService.updateMarca(id, marca);
        return ResponseEntity.ok(mar);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.ok("Marca eliminada correctamente");
    }
}
