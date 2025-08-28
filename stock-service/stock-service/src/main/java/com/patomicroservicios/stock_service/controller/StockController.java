package com.patomicroservicios.stock_service.controller;

import com.patomicroservicios.stock_service.dto.request.StockPatch;
import com.patomicroservicios.stock_service.dto.request.StockCreateDTO;
import com.patomicroservicios.stock_service.dto.response.StockDTO;
import com.patomicroservicios.stock_service.model.Stock;
import com.patomicroservicios.stock_service.service.IStockService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    IStockService stockService;
    @Operation(
            summary="Alta de Stock",
            description= "Este endpoint permite registrar el stock de un producto por primera vez"
    )
    @PostMapping
    public ResponseEntity<StockDTO> addStock(@Valid @RequestBody StockCreateDTO stock) {
        StockDTO created = stockService.addStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary="Obtener todo el Stock",
            description= "Este endpoint permite obtener el stock de todos los productos registrados"
    )
    @GetMapping("/get/all")
    public ResponseEntity<List<Stock>> getStockList() {
        List<Stock> stocks = stockService.getAllStock();
        return ResponseEntity.ok(stocks);
    }

    @Operation(
            summary="Obtener Stock de un Producto",
            description= "Este endpoint permite obtener el stock de un producto en específico"
    )
    @GetMapping("/get/{productId}")
    public ResponseEntity<StockDTO> getStock(@PathVariable Long productId) {
        StockDTO stock = stockService.getStockDTO(productId);
        return ResponseEntity.ok(stock);
    }

    @Operation(
            summary="Editar Stock de un Producto",
            description= "Este endpoint permite modificar la cantidad de stock de un producto"
    )
    @PatchMapping("/patch")
    public ResponseEntity<StockDTO> patchStock(@PathVariable Long productId, @RequestBody StockPatch stock) {
            StockDTO nuevoStock=stockService.editStock(stock.getIdProducto(), stock.getCantidad());
            return ResponseEntity.ok(nuevoStock);
    }


}
