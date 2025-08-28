package com.patomicroservicios.carrito_service.service;

import com.patomicroservicios.carrito_service.dto.CarritoDTO;
import com.patomicroservicios.carrito_service.model.Carrito;

import java.util.List;

public interface ICarritoService {
    Carrito agregarProducto(Long idCarrito, Long idProducto, int cantidad);
    void eliminarProducto(Long idCarrito, Long idProducto);
    CarritoDTO getCarritoandProductos(Long idCarrito);
    List<Carrito> getAllCarritos();
    void altaCarrito(String idUser);
    void vaciarCarrito(Long idCarrito);
    Carrito editarCantidadProducto(Long idCarrito, Long idProducto, int cantidad);
}
