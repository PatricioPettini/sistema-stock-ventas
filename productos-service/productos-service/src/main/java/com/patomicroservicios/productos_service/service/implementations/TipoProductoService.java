package com.patomicroservicios.productos_service.service.implementations;

import com.patomicroservicios.productos_service.Exceptions.TipoProductoNoEncontradoException;
import com.patomicroservicios.productos_service.dto.response.TipoProductoDTO;
import com.patomicroservicios.productos_service.model.TipoProducto;
import com.patomicroservicios.productos_service.repository.ITipoProductoRepository;
import com.patomicroservicios.productos_service.service.interfaces.ITipoProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TipoProductoService implements ITipoProductoService {

    @Autowired
    ITipoProductoRepository tipoProductoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public TipoProducto getTipoProducto(Long id) {
        return tipoProductoRepository.findById(id).orElseThrow(()->new TipoProductoNoEncontradoException(id));
    }

    public TipoProductoDTO getTipoProductoDTO(Long id){
        return modelMapper.map(getTipoProducto(id),TipoProductoDTO.class);
    }

    @Override
    public List<TipoProducto> getAll() {
        return tipoProductoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        TipoProducto tipo=getTipoProducto(id);
        tipoProductoRepository.deleteById(id);
    }

    @Override
    public TipoProductoDTO add(TipoProducto tipo) {
        tipoProductoRepository.save(tipo);
        return modelMapper.map(tipo,TipoProductoDTO.class);
    }

    @Override
    public TipoProductoDTO update(Long id, TipoProducto tipo) {
        TipoProducto tip=getTipoProducto(id);
        tip.setTipo(tipo.getTipo());
        tipoProductoRepository.save(tip);
        return modelMapper.map(tipo,TipoProductoDTO.class);
    }
}
