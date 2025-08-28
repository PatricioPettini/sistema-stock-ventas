package com.patomicroservicios.productos_service.service.interfaces;

import com.patomicroservicios.productos_service.dto.response.TipoProductoDTO;
import com.patomicroservicios.productos_service.model.TipoProducto;

import java.util.List;

public interface ITipoProductoService {
    TipoProducto getTipoProducto(Long id);
    TipoProductoDTO getTipoProductoDTO(Long id);
    List<TipoProducto> getAll();
    void delete(Long id);
    TipoProductoDTO add(TipoProducto tipo);
    TipoProductoDTO update(Long id, TipoProducto tipo);
}
