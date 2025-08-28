package com.patomicroservicios.productos_service.service.implementations;

import com.patomicroservicios.productos_service.Exceptions.ProductoActivoException;
import com.patomicroservicios.productos_service.Exceptions.ProductoInactivoException;
import com.patomicroservicios.productos_service.Exceptions.ProductoNoEncontradoException;
import com.patomicroservicios.productos_service.dto.request.ProductPatchDTO;
import com.patomicroservicios.productos_service.dto.request.ProductoUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.MarcaDTO;
import com.patomicroservicios.productos_service.dto.response.ProductoDTO;
import com.patomicroservicios.productos_service.dto.response.TipoProductoDTO;
import com.patomicroservicios.productos_service.model.Marca;
import com.patomicroservicios.productos_service.model.Producto;
import com.patomicroservicios.productos_service.model.TipoProducto;
import com.patomicroservicios.productos_service.repository.IProductoRepository;
import com.patomicroservicios.productos_service.service.interfaces.IMarcaService;
import com.patomicroservicios.productos_service.service.interfaces.IProductoService;
import com.patomicroservicios.productos_service.service.interfaces.ITipoProductoService;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoRepository productoRepository;

    @Autowired
    ITipoProductoService tipoProductoService;

    @Autowired
    IMarcaService marcaService;

    @Autowired
    ModelMapper modelMapper;

    //dar de alta un nuevo producto
    @Override
    public ProductoDTO altaProducto(Producto producto) {
        ProductoDTO dto = modelMapper.map(producto,ProductoDTO.class);
        if(producto.getIdMarca()!=null){
            Marca mar=marcaService.getMarca(producto.getIdMarca());
            dto.setMarca(modelMapper.map(mar,MarcaDTO.class));
        }
        productoRepository.save(producto);
        return dto;
    }

    @Override
    public void eliminarProducto(Long codigoProducto) {
        Producto producto=getProducto(codigoProducto);
        productoRepository.deleteById(codigoProducto);
    }

    //modificar completamente el producto
    @Override
    public ProductoDTO editarProducto(ProductoUpdateDTO producto, Long codigoProducto) {

        Producto product=getProducto(codigoProducto);

        product.setNombre(producto.getNombre());
        product.setPrecioIndividual(producto.getPrecioIndividual());
        product.setEstado(producto.getEstado());

        Long idMarca=producto.getIdMarca();
        if (idMarca != null) {
            product.setIdMarca(idMarca);
        }
        Long idTipoProducto = producto.getIdTipoProducto();
        if (idTipoProducto != null) {
            product.setIdTipoProducto(idTipoProducto);
        }

        productoRepository.save(product);

        ProductoDTO dto = modelMapper.map(producto,ProductoDTO.class);
        if (producto.getIdMarca() != null) {
            MarcaDTO marcaDto = marcaService.getMarcaDTO(producto.getIdMarca());
            if (marcaDto != null) dto.setMarca(marcaDto);
        }
        if (producto.getIdTipoProducto() != null) {
            TipoProductoDTO tipoDto = tipoProductoService.getTipoProductoDTO(producto.getIdTipoProducto());
            if (tipoDto != null) dto.setTipo(tipoDto);
        }

        return dto;
    }

//    @Override
//    public List<Producto> getAllProductos() {
//        return productoRepository.findAll();
//    }

    public List<ProductoDTO> getAllDto() {
        return productoRepository.findAll().stream()
                .map(this::toDto)   // acá usamos ModelMapper
                .collect(Collectors.toList());
    }

    private ProductoDTO toDto(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    // obtener productos DTO
    // implemento Circuit Breaker para manejar fallos en el servicio externo de Marca
    @Override
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackProductoNoEncontrado")
    @Retry(name = "retryGetProductoDTO")
    public ProductoDTO getProductoDTO(Long codigoProducto){
        return productoRepository.findById(codigoProducto).map(
                producto -> {
                    ProductoDTO dto = modelMapper.map(producto, ProductoDTO.class);
                    Long idMarca = producto.getIdMarca();
                    if (idMarca != null) {
                        Marca mar = marcaService.getMarca(producto.getIdMarca());
                        if (mar != null) {
                            dto.setMarca(modelMapper.map(mar, MarcaDTO.class));
                        }
                    }
                    Long idTipoProducto = producto.getIdTipoProducto();
                    if (idTipoProducto != null) {
                        TipoProducto tipo = tipoProductoService.getTipoProducto(producto.getIdTipoProducto());
                        if (tipo != null) {
                            dto.setTipo(modelMapper.map(tipo, TipoProductoDTO.class));
                        }
                    }
                    return dto;

                }
        ).orElseThrow(ProductoNoEncontradoException::new);
    }

    //modificar algun campos del producto
    @Override
    public ProductoDTO patchProduct(Long codigoProducto, ProductPatchDTO producto) {
        Producto product=getProducto(codigoProducto);
        if(producto.getNombre()!=null) product.setNombre(producto.getNombre());
        if (producto.getPrecioIndividual() != null) {
            double precio = producto.getPrecioIndividual();
            if (precio <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a 0");
            }
            product.setPrecioIndividual(precio);
        }
        if(producto.getIdMarca()!=null) product.setIdMarca(producto.getIdMarca());
        if(producto.getIdTipoProducto()!=null) product.setIdTipoProducto(producto.getIdTipoProducto());
        if(producto.getEstado()!=null) product.setEstado(producto.getEstado());
        Producto prod = productoRepository.save(product);
        return modelMapper.map(prod,ProductoDTO.class);
    }

    @Override
    public ProductoDTO inactivarProducto(Long id) {
        Producto producto=getProducto(id);
        producto.inactivar();
        productoRepository.save(producto);
        return getProductoDTO(id);
    }

    @Override
    public ProductoDTO ActivarProducto(Long id) {
        Producto producto=getProducto(id);
        producto.activar();
        productoRepository.save(producto);
        return getProductoDTO(id);
    }

    @Override
    public List<ProductoDTO> filter(Long idMarca, Long idTipoProducto, Boolean estado) {
        Long marca = (idMarca != null && idMarca > 0) ? idMarca : null;
        Long tipo  = (idTipoProducto != null && idTipoProducto > 0) ? idTipoProducto : null;
        Boolean estadoProducto = estado!=null && estado.equals(true);
        return getAllDto().stream()
                .filter(p ->
                        (estado == null || p.getEstado().equals(estadoProducto)) &&
                                (tipo   == null || p.getTipo().getId().equals(tipo)) &&
                                (marca  == null || p.getMarca().getId().equals(marca))
                )
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> orderByPrecio(Boolean ascendente) {
        return getAllDto().stream()
                .sorted(Boolean.TRUE.equals(ascendente)
                        ? Comparator.comparing(ProductoDTO::getPrecioIndividual).reversed()
                        : Comparator.comparing(ProductoDTO::getPrecioIndividual))
                .collect(Collectors.toList());
    }

    // obtener producto
    public Producto getProducto(Long codigoProducto){
        Producto producto=productoRepository.findById(codigoProducto).orElseThrow(ProductoNoEncontradoException::new);
        return producto;
    }

    public ProductoDTO fallbackProductoNoEncontrado(Long codigoProducto, Throwable throwable) {
        System.out.println("⚠️ Fallback activado por excepción: " + throwable.getMessage());

        ProductoDTO productoFallback = new ProductoDTO();
        productoFallback.setCodigo(codigoProducto);
        productoFallback.setNombre("Producto no disponible");
        productoFallback.setEstado(false); // o como manejes el estado

        return productoFallback;
    }

}
