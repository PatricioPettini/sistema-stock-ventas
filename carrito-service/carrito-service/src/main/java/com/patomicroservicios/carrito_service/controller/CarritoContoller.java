package com.patomicroservicios.carrito_service.controller;

import com.patomicroservicios.carrito_service.dto.ProductoDTO;
import com.patomicroservicios.carrito_service.dto.request.CarritoProductsDTO;
import com.patomicroservicios.carrito_service.dto.CarritoDTO;
import com.patomicroservicios.carrito_service.dto.request.CarritoProductsDeleteDTO;
import com.patomicroservicios.carrito_service.model.Carrito;
import com.patomicroservicios.carrito_service.service.ICarritoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoContoller {

    @Autowired
    ICarritoService carritoService;

    @Operation(
        summary="Obtener un Carrito",
        description= "Este endpoint permite obtener un carrito en base a su id"
    )
    @GetMapping("/get/{idCarrito}")
    public ResponseEntity<CarritoDTO> getCarrito(@PathVariable Long idCarrito){
        CarritoDTO carritoDTO= carritoService.getCarritoandProductos(idCarrito);
        return ResponseEntity.ok(carritoDTO);
    }
    @Operation(
            summary="Obtener todos los Carritos",
            description= "Este endpoint permite obtener todos los carritos"
    )
    @GetMapping("/get/all")
    public List<Carrito> getAllCarritos(){
        return carritoService.getAllCarritos();
    }

    @Operation(
            summary="Crear Carrito",
            description= "Este endpoint permite simular el alta de un carrito"
    )
    @PostMapping("/{idUser}")
    public ResponseEntity<String> altaCarrito(@PathVariable String idUser){
        carritoService.altaCarrito(idUser);
        return ResponseEntity.ok("Se creo el carrito correctamente!");
    }

    @Operation(
            summary="Añadir Productos al Carrito",
            description= "Este endpoint permite agregar productos a un carrito"
    )
    @PostMapping("/add/producto")
    public ResponseEntity<Carrito> addProductosCarrito(@RequestBody CarritoProductsDTO dto){
        Carrito carrito=carritoService.agregarProducto(dto.getIdCarrito(),dto.getIdProducto(), dto.getCantidad());
        return ResponseEntity.ok(carrito);
    }

    @Operation(
            summary="Editar la cantidad de un producto en el Carrito",
            description= "Este endpoint permite obtener un carrito en base a su id"
    )
    @PutMapping("/put/producto")
    public ResponseEntity<Carrito> editarCantidadProductoCarrito(@RequestBody CarritoProductsDTO dto){
        Carrito carrito=carritoService.editarCantidadProducto(dto.getIdCarrito(), dto.getIdProducto(),dto.getCantidad());
        return ResponseEntity.ok(carrito);
    }

    @Operation(
            summary="Eliminar Producto del Carrito",
            description= "Este endpoint permite eliminar un producto del carrito"
    )
    @DeleteMapping("/delete/producto")
    public ResponseEntity<String> deleteProductosCarritos(@RequestBody CarritoProductsDeleteDTO dto){
        carritoService.eliminarProducto(dto.getIdCarrito(),dto.getIdProducto());
        return ResponseEntity.ok("Se elimino el producto del carrito!!");
    }

    @PutMapping("/vaciar/{idCarrito}")
    public ResponseEntity<String> vaciarCarrito(@PathVariable Long idCarrito){
        carritoService.vaciarCarrito(idCarrito);
        return ResponseEntity.ok("Se vacio el carrito!");
    }
}
