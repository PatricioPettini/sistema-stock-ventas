package com.patomicroservicios.productos_service.service.implementations;

import com.patomicroservicios.productos_service.Exceptions.ProductoNoEncontradoException;
import com.patomicroservicios.productos_service.dto.request.ProductCreateDTO;
import com.patomicroservicios.productos_service.dto.request.ProductPatchDTO;
import com.patomicroservicios.productos_service.dto.request.ProductoUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.ProductoDTO;
import com.patomicroservicios.productos_service.model.Marca;
import com.patomicroservicios.productos_service.model.Producto;
import com.patomicroservicios.productos_service.model.Categoria;
import com.patomicroservicios.productos_service.repository.IProductoRepository;
import com.patomicroservicios.productos_service.service.interfaces.IMarcaService;
import com.patomicroservicios.productos_service.service.interfaces.IProductoService;
import com.patomicroservicios.productos_service.service.interfaces.ICategoriaService;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoRepository productoRepository;

    @Autowired
    ICategoriaService categoriaService;

    @Autowired
    IMarcaService marcaService;

    @Autowired
    ModelMapper modelMapper;

    //dar de alta un nuevo producto
    @Override
    public ProductoDTO altaProducto(ProductCreateDTO producto) {
        Producto prod=modelMapper.map(producto,Producto.class);
        Long idMarca=producto.getIdMarca();
        if(idMarca!=null && marcaService.getMarca(idMarca)!=null){
            prod.setMarca(marcaService.getMarca(idMarca));
        }
        Long idCategoria=producto.getIdCategoria();
        if(idCategoria!=null && categoriaService.getCategoria(idCategoria)!=null){
            prod.setCategoria(categoriaService.getCategoria(idCategoria));
        }
//        prod.setActualizadoEn(LocalDate.now());
//        prod.setCreadoEn(LocalDate.now());
        Producto guardado=productoRepository.save(prod);
        return modelMapper.map(guardado,ProductoDTO.class);
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
            Marca mar=marcaService.getMarca(idMarca);
            product.setMarca(mar);
        }
        Long idCategoria = producto.getIdCategoria();
        if (idCategoria != null) {
            Categoria categoria=categoriaService.getCategoria(idCategoria);
            product.setCategoria(categoria);
        }
//        product.setActualizadoEn(LocalDate.now());
        productoRepository.save(product);

        return modelMapper.map(producto,ProductoDTO.class);
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
                    return dto;
                }
        ).orElseThrow(()->new ProductoNoEncontradoException(codigoProducto));
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
        if(producto.getIdMarca()!=null){
            Marca mar=marcaService.getMarca(producto.getIdMarca());
            product.setMarca(mar);
        }
        if(producto.getIdCategoria()!=null){
            Categoria categoria=categoriaService.getCategoria(producto.getIdCategoria());
            product.setCategoria(categoria);
        }
        if(producto.getEstado()!=null) product.setEstado(producto.getEstado());
//        product.setActualizadoEn(LocalDate.now());
        Producto prod = productoRepository.save(product);
        return modelMapper.map(prod,ProductoDTO.class);
    }

    @Override
    public ProductoDTO inactivarProducto(Long id) {
        Producto producto=getProducto(id);
        producto.inactivar();
//        producto.setActualizadoEn(LocalDate.now());
        productoRepository.save(producto);
        return getProductoDTO(id);
    }

    @Override
    public ProductoDTO ActivarProducto(Long id) {
        Producto producto=getProducto(id);
        producto.activar();
//        producto.setActualizadoEn(LocalDate.now());
        productoRepository.save(producto);
        return getProductoDTO(id);
    }

    @Override
    public List<ProductoDTO> filter(Long idMarca, Long idCategoria, Boolean estado) {
        Long marca = (idMarca != null && idMarca > 0) ? idMarca : null;
        Long categoria  = (idCategoria != null && idCategoria > 0) ? idCategoria : null;
        Boolean estadoProducto = estado!=null && estado.equals(true);
        return getAllDto().stream()
                .filter(p ->
                        (estado == null || p.getEstado().equals(estadoProducto)) &&
                                (categoria   == null || p.getCategoria().getId().equals(categoria)) &&
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
        Producto producto=productoRepository.findById(codigoProducto).orElseThrow(()->new ProductoNoEncontradoException(codigoProducto));
        return producto;
    }

//    public ProductoDTO fallbackProductoNoEncontrado(Long codigoProducto, Throwable throwable) {
//        System.out.println("⚠️ Fallback activado por excepción: " + throwable.getMessage());
//
//        ProductoDTO productoFallback = new ProductoDTO();
//        productoFallback.setCodigo(codigoProducto);
//        productoFallback.setNombre("Producto no disponible");
//        productoFallback.setEstado(false); // o como manejes el estado
//
//        return productoFallback;
//    }

}
