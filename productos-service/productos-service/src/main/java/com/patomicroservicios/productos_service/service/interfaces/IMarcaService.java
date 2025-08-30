package com.patomicroservicios.productos_service.service.interfaces;

import com.patomicroservicios.productos_service.dto.request.MarcaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.MarcaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.model.Marca;

import java.util.List;

public interface IMarcaService {
    MarcaDTO getMarcaDTO(Long id);
    List<MarcaDTO> getAll();
    MarcaDTO addMarca(MarcaCreateDTO marca);
    void deleteMarca(Long id);
    MarcaDTO updateMarca(Long id, MarcaUpdateDTO marca);
    Marca toModel(MarcaDTO marcaDto);
}
