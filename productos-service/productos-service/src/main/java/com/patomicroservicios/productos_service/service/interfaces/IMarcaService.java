package com.patomicroservicios.productos_service.service.interfaces;

import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.model.Marca;

import java.util.List;

public interface IMarcaService {
    Marca getMarca(Long id);
    MarcaDTO getMarcaDTO(Long id);
    List<Marca> getAll();
    MarcaDTO addMarca(Marca marca);
    void deleteMarca(Long id);
    MarcaDTO updateMarca(Long id,Marca marca);
}
