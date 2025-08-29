package com.patomicroservicios.productos_service.service.interfaces;

import com.patomicroservicios.productos_service.dto.request.ProductCreateDTO;
import com.patomicroservicios.productos_service.dto.request.ProductPatchDTO;
import com.patomicroservicios.productos_service.dto.request.ProductoUpdateDTO;
import com.patomicroservicios.productos_service.dto.response.ProductoDTO;
import com.patomicroservicios.productos_service.model.Producto;

import java.util.List;

public interface IProductoService {
    ProductoDTO altaProducto(ProductCreateDTO producto);
    void eliminarProducto(Long codigoProducto);
    ProductoDTO editarProducto(ProductoUpdateDTO producto, Long codigoProducto);
    List<ProductoDTO> getAllDto();
    ProductoDTO getProductoDTO(Long codigoProducto);
    ProductoDTO patchProduct(Long codigo, ProductPatchDTO prod);
    ProductoDTO inactivarProducto(Long id);
    ProductoDTO ActivarProducto(Long id);
    List<ProductoDTO> filter(Long idMarca, Long idCategoria, Boolean estado);
    List<ProductoDTO> orderByPrecio(Boolean ascendente);
//    List<ProductoDTO> getProductsById(List<Long> ids);
}
