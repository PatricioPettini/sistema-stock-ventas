package com.patomicroservicios.productos_service.service.implementations;

import com.patomicroservicios.productos_service.Exceptions.MarcaNoEncontradaException;
import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.model.Marca;
import com.patomicroservicios.productos_service.repository.IMarcaRepository;
import com.patomicroservicios.productos_service.service.interfaces.IMarcaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService implements IMarcaService {
    @Autowired
    IMarcaRepository marcaRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Marca getMarca(Long id) {
        return marcaRepository.findById(id).orElseThrow(() -> new MarcaNoEncontradaException(id));
    }

    public MarcaDTO getMarcaDTO(Long id){
        return modelMapper.map(getMarca(id),MarcaDTO.class);
    }

    @Override
    public List<Marca> getAll() {
        return marcaRepository.findAll();
    }

    @Override
    public MarcaDTO addMarca(Marca marca) {
        marcaRepository.save(marca);
        return modelMapper.map(marca,MarcaDTO.class);
    }

    @Override
    public void deleteMarca(Long id) {
        Marca marca=getMarca(id);
        marcaRepository.deleteById(id);
    }

    @Override
    public MarcaDTO updateMarca(Long id, Marca marca) {
        Marca mar=getMarca(id);
        mar.setMarca(marca.getMarca());
        marcaRepository.save(mar);
        return modelMapper.map(mar,MarcaDTO.class);
    }
}
