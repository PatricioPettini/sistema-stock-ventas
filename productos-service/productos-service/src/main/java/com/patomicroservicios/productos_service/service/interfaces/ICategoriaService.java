package com.patomicroservicios.productos_service.service.interfaces;

import com.patomicroservicios.productos_service.dto.request.CategoriaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.CategoriaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.CategoriaDTO;
import com.patomicroservicios.productos_service.model.Categoria;

import java.util.List;

public interface ICategoriaService {
    Categoria getCategoria(Long id);
    CategoriaDTO getCategoriaDTO(Long id);
    List<Categoria> getAll();
    void delete(Long id);
    CategoriaDTO add(CategoriaCreateDTO categoria);
    CategoriaDTO update(Long id, CategoriaUpdateDTO categoria);
}
