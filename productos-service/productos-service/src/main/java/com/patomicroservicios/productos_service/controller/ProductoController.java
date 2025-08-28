package com.patomicroservicios.productos_service.controller;

import com.patomicroservicios.productos_service.dto.request.ProductCreateDTO;
import com.patomicroservicios.productos_service.dto.request.ProductFilterDTO;
import com.patomicroservicios.productos_service.dto.request.ProductPatchDTO;
import com.patomicroservicios.productos_service.dto.request.ProductoUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.ProductoDTO;
import com.patomicroservicios.productos_service.model.Producto;
import com.patomicroservicios.productos_service.service.interfaces.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    IProductoService productoService;

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    ModelMapper modelMapper;
    @Operation(
            summary="Obtener Productos",
            description= "Este endpoint permite obtener un producto en base a su id"
    )
    @GetMapping("/get/{codigoProducto}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long codigoProducto) {
        return ResponseEntity.ok(productoService.getProductoDTO(codigoProducto));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductoDTO>> getProductosFiltrados(
            @RequestBody ProductFilterDTO dto) {
        return ResponseEntity.ok(productoService.filter(dto.getIdMarca(), dto.getIdTipoProducto(), dto.getEstado()));
    }

    @GetMapping("/order/precio/{ascendente}")
    public ResponseEntity<List<ProductoDTO>> getProductosOrderByPrecio(@PathVariable Boolean ascendente) {
        return ResponseEntity.ok(productoService.orderByPrecio(ascendente));
    }

    @Operation(
            summary="Editar Producto",
            description= "Este endpoint permite editar algunos campos de un producto"
    )
    @PatchMapping("/patch/{codigoProducto}")
    public ResponseEntity<ProductoDTO> patchProducto(@PathVariable Long codigoProducto,@Valid @RequestBody ProductPatchDTO producto) {
        return ResponseEntity.ok(productoService.patchProduct(codigoProducto,producto));
    }

    @Operation(
            summary="Inactivar Producto",
            description= "Este endpoint permite inactivar un producto"
    )
    @PatchMapping("/inactivar/{codigoProducto}")
    public ResponseEntity<ProductoDTO> inactivarProducto(@PathVariable Long codigoProducto) {
        return ResponseEntity.ok(productoService.inactivarProducto(codigoProducto));
    }

    @Operation(
            summary="Activar Producto",
            description= "Este endpoint permite activar un producto"
    )
    @PatchMapping("/activar/{codigoProducto}")
    public ResponseEntity<ProductoDTO> activarProducto(@PathVariable Long codigoProducto) {
        return ResponseEntity.ok(productoService.ActivarProducto(codigoProducto));
    }

    @Operation(
            summary="Obtener todos los Productos",
            description= "Este endpoint permite obtener todos los productos"
    )
    @GetMapping("/get/all")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        System.out.println("server: " + serverPort); //ver el funcionamiento de Load Balancer
        List<ProductoDTO> productos = productoService.getAllDto();
        return ResponseEntity.ok(productos);

    }
    @Operation(
            summary="Alta Producto",
            description= "Este endpoint permite agregar un producto"
    )
    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductoDTO> addProduct(@Valid @RequestBody ProductCreateDTO producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.altaProducto(producto));
    }
    @Operation(
            summary="Editar Producto",
            description= "Este endpoint permite editar tods los campos de un producto"
    )
    @PutMapping("/put/{codigoProducto}")
    public ResponseEntity<String> putProduct(@PathVariable Long codigoProducto,@Valid @RequestBody ProductoUpdateDTO producto) {
        productoService.editarProducto(producto, codigoProducto);
        return ResponseEntity.ok("Producto editado correctamente");
    }
    @Operation(
            summary="Eliminar Producto",
            description= "Este endpoint permite eliminar un producto"
    )
    @DeleteMapping("/delete/{codigoProducto}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long codigoProducto) {
        productoService.eliminarProducto(codigoProducto);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}
