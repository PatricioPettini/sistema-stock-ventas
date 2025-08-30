package com.patomicroservicios.carrito_service.service;

import com.patomicroservicios.carrito_service.dto.response.CarritoDTO;
import com.patomicroservicios.carrito_service.model.Carrito;

import java.util.List;

public interface ICarritoService {
    CarritoDTO agregarProducto(Long idCarrito, Long idProducto, int cantidad);
    void eliminarProducto(Long idCarrito, Long idProducto);
    CarritoDTO getCarritoandProductos(Long idCarrito);
    List<CarritoDTO> getAllCarritos();
    void altaCarrito(String idUser);
    void vaciarCarrito(Long idCarrito);
    CarritoDTO editarCantidadProducto(Long idCarrito, Long idProducto, int cantidad);
}
