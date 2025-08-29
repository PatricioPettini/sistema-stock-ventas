package com.patomicroservicios.productos_service.service.implementations;

import com.patomicroservicios.productos_service.Exceptions.MarcaNoEncontradaException;
import com.patomicroservicios.productos_service.Exceptions.MarcaRegistradaException;
import com.patomicroservicios.productos_service.dto.request.MarcaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.MarcaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.model.Marca;
import com.patomicroservicios.productos_service.repository.IMarcaRepository;
import com.patomicroservicios.productos_service.service.interfaces.IMarcaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public MarcaDTO addMarca(MarcaCreateDTO marca) {
        String nombre=marca.getDescripcion();
        if(getMarcaByDescription(nombre)!=null) throw new MarcaRegistradaException(nombre);
        Marca mar=modelMapper.map(marca,Marca.class);
        mar.setDescripcion(nombre);

        Marca guardada=marcaRepository.save(mar);
        return modelMapper.map(guardada,MarcaDTO.class);
    }

    public Marca getMarcaByDescription(String nombre){
        return marcaRepository.getByDescripcion(nombre);
    }

    @Override
    public void deleteMarca(Long id) {
        Marca marca=getMarca(id);
        marcaRepository.deleteById(id);
    }

    @Override
    public MarcaDTO updateMarca(Long id, MarcaUpdateDTO marca) {
        Marca mar=getMarca(id);
        mar.setDescripcion(marca.getDescripcion());
//        mar.setActualizadoEn(LocalDate.now());
        Marca marca1=marcaRepository.save(mar);
        return modelMapper.map(marca1,MarcaDTO.class);
    }
}
