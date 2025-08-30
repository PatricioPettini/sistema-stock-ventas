package com.patomicroservicios.productos_service.service.interfaces;

import com.patomicroservicios.productos_service.dto.request.CategoriaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.CategoriaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.CategoriaDTO;
import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.model.Categoria;
import com.patomicroservicios.productos_service.model.Marca;

import java.util.List;

public interface ICategoriaService {
    CategoriaDTO getCategoriaDTO(Long id);
    List<CategoriaDTO> getAll();
    void delete(Long id);
    CategoriaDTO add(CategoriaCreateDTO categoria);
    CategoriaDTO update(Long id, CategoriaUpdateDTO categoria);
    Categoria toModel(CategoriaDTO categoriaDTO);
}
