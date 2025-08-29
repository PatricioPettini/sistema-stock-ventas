package com.patomicroservicios.productos_service.service.implementations;

import com.patomicroservicios.productos_service.Exceptions.CategoriaNoEncontradaException;
import com.patomicroservicios.productos_service.Exceptions.CategoriaRegistradaException;
import com.patomicroservicios.productos_service.dto.request.CategoriaCreateDTO;
import com.patomicroservicios.productos_service.dto.request.CategoriaUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.CategoriaDTO;
import com.patomicroservicios.productos_service.model.Categoria;
import com.patomicroservicios.productos_service.repository.ICategoriaRepository;
import com.patomicroservicios.productos_service.service.interfaces.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    ICategoriaRepository categoriaRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Categoria getCategoria(Long id) {
        return categoriaRepository.findById(id).orElseThrow(()->new CategoriaNoEncontradaException(id));
    }

    public CategoriaDTO getCategoriaDTO(Long id){
        return modelMapper.map(getCategoria(id), CategoriaDTO.class);
    }

    @Override
    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Categoria cat=getCategoria(id);
        categoriaRepository.deleteById(cat.getId());
    }

    @Override
    public CategoriaDTO add(CategoriaCreateDTO categoriaCreateDTO) {
//        categoria.setCreadoEn(LocalDate.now());
//        categoria.setActualizadoEn(LocalDate.now());
        String nombre=categoriaCreateDTO.getDescripcion();
        Categoria categoria=getCategoriaByDescripcion(nombre);
        if(categoria != null) throw new CategoriaRegistradaException(nombre);
        categoria= new Categoria();
        if(categoriaCreateDTO.getCategoriaPadre()!=null){
            Long idCategoriaPadre=categoriaCreateDTO.getCategoriaPadre().getId();
            Categoria cat=getCategoria(idCategoriaPadre);
            categoria.setCategoriaPadre(cat);
        }
        categoria.setDescripcion(nombre);
        categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public Categoria getCategoriaByDescripcion(String descipcion){
        return categoriaRepository.getByDescripcion(descipcion);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaUpdateDTO categoria) {
        Categoria cat=getCategoria(id);
        cat.setDescripcion(categoria.getDescripcion());
        if(categoria.getCategoriaPadre()!=null){
            Long idCategoriaPadre=categoria.getCategoriaPadre().getId();
            Categoria cate=getCategoria(idCategoriaPadre);
            cat.setCategoriaPadre(cate);
        }
//      cat.setActualizadoEn(LocalDate.now());
        Categoria ca=categoriaRepository.save(cat);
        return modelMapper.map(ca, CategoriaDTO.class);
    }
}
