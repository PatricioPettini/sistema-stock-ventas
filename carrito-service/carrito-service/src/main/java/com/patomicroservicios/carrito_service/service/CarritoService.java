package com.patomicroservicios.carrito_service.service;

import com.patomicroservicios.carrito_service.Exceptions.CarritoNoEncontradoException;
import com.patomicroservicios.carrito_service.Exceptions.ProductoInactivoException;
import com.patomicroservicios.carrito_service.Exceptions.ProductoNoEncontradoException;
import com.patomicroservicios.carrito_service.Exceptions.StockException;
import com.patomicroservicios.carrito_service.dto.CarritoDTO;
import com.patomicroservicios.carrito_service.dto.ProductoDTO;
import com.patomicroservicios.carrito_service.dto.request.StockPatchDTO;
import com.patomicroservicios.carrito_service.dto.response.StockDTO;
import com.patomicroservicios.carrito_service.model.Carrito;
import com.patomicroservicios.carrito_service.model.ProductoCantidad;
import com.patomicroservicios.carrito_service.repository.ICarritoRepository;
import com.patomicroservicios.carrito_service.repository.productoAPI;
import com.patomicroservicios.carrito_service.repository.stockAPI;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
public class CarritoService implements ICarritoService{

    @Autowired
    ICarritoRepository carritoRepository;

    @Autowired
    productoAPI productoAPI;

    @Autowired
    stockAPI stockAPI;

    @Autowired
    ModelMapper modelMapper;

    //agregar producto al carrito
    @Override
    public Carrito agregarProducto(Long idCarrito, Long idProducto, int cantidad) {
        Carrito carrito = carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        ProductoDTO productoDTO = productoAPI.getProducto(idProducto);
        if (productoDTO == null) throw new ProductoNoEncontradoException();
        if(productoDTO.getEstado().equals(false)) throw new ProductoInactivoException();
        if(cantidad<1) throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        StockDTO stockDTO = validarStock(idProducto, cantidad);
        ProductoCantidad productoCantidad=new ProductoCantidad(idProducto, cantidad);
        stockAPI.editStock(new StockPatchDTO(idProducto,stockDTO.getCantidad()-cantidad));
        carrito.getProductList().add(productoCantidad);
        carrito=actualizarSumaTotal(carrito);
        return carritoRepository.save(carrito);
    }

    //validar stock disponible
    private StockDTO validarStock(Long idProducto, int cantidad) {
        StockDTO stockDTO=stockAPI.getCantidadStockPoducto(idProducto);
        if(cantidad >stockDTO.getCantidad()) throw new StockException();
        ProductoDTO productoDTO=productoAPI.getProducto(idProducto);
        if(productoDTO.getEstado().equals(false)) throw new ProductoInactivoException();
        return stockDTO;
    }

    //actualizar total del carrito
    private Carrito actualizarSumaTotal(Carrito carrito) {
        double suma = 0;
        for(ProductoCantidad pc : carrito.getProductList()){
            ProductoDTO productoDTO=productoAPI.getProducto(pc.getIdProducto());
            suma+=productoDTO.getPrecioIndividual()*pc.getCantidad();
        }
        carrito.setTotalPrice(suma);
        return carrito;
    }

    //eliminar poroducto del carrito
    @Override
    @Transactional
    public void eliminarProducto(Long idCarrito, Long idProducto) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(CarritoNoEncontradoException::new);

// 🔁 1. Buscar la cantidad antes de eliminar
        int cantidad = carrito.getProductList().stream()
                .filter(p -> p.getIdProducto().equals(idProducto))
                .mapToInt(ProductoCantidad::getCantidad)
                .findFirst()
                .orElse(0);

// 🔁 2. Eliminar el producto
        boolean eliminado = carrito.getProductList()
                .removeIf(p -> p.getIdProducto().equals(idProducto));

        if (eliminado) {
            StockDTO stockDTO = stockAPI.getCantidadStockPoducto(idProducto);

            // 🔁 3. Actualizar stock correctamente
            stockAPI.editStock(new StockPatchDTO(idProducto, cantidad + stockDTO.getCantidad()));

            carrito = actualizarSumaTotal(carrito);
            carritoRepository.save(carrito);
        } else {
            throw new ProductoNoEncontradoException();
        }

    }

    //obtener el carrito completo con los productos incluidos
    @Override
    public CarritoDTO getCarritoandProductos(Long idCarrito) {
        Carrito carrito=carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        List<ProductoDTO> listaProductos= new ArrayList<>();
        for(ProductoCantidad pc : carrito.getProductList()){
            listaProductos.add(productoAPI.getProducto(pc.getIdProducto()));
        }
        CarritoDTO carritoDTO= new CarritoDTO(carrito.getId(),carrito.getTotalPrice(),listaProductos);
        return carritoDTO;
    }

    @Override
    public List<Carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    //crear nuevo carrito
    @Override
    public void altaCarrito(String idUser) {
        Carrito carrito= new Carrito();
        carrito.setIdUser(idUser);
        carrito.setTotalPrice(null);
        carritoRepository.save(carrito);
    }

    @Override
    public void vaciarCarrito(Long idCarrito) {
        Carrito carrito=carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        for(ProductoCantidad pc : carrito.getProductList()){
            Long idProducto=pc.getIdProducto();
            int cantidad=pc.getCantidad();
            StockDTO dto=stockAPI.getCantidadStockPoducto(idProducto);
            stockAPI.editStock(new StockPatchDTO(idProducto,dto.getCantidad()+cantidad));
        }
        carrito.setProductList(null);
        carritoRepository.save(carrito);
    }
    @Override
    public Carrito editarCantidadProducto(Long idCarrito, Long idProducto, int cantidad) {
        Carrito carrito=carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        ProductoDTO productoDTO=productoAPI.getProducto(idProducto);
        if(productoDTO==null) throw new ProductoNoEncontradoException();
        if(productoDTO.getEstado().equals(false)) throw new ProductoInactivoException();
        if(cantidad<1) throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        List<ProductoCantidad> productoCantidadList= new ArrayList<>();
        for(ProductoCantidad pc : carrito.getProductList()){
            if(pc.getIdProducto().equals(idProducto)){
                int cantidadAnterior=pc.getCantidad();
                pc.setCantidad(cantidad);
                int diferencia = cantidad - cantidadAnterior;
                StockDTO stockDTO=stockAPI.getCantidadStockPoducto(idProducto);
                if (diferencia > 0) {
                    if (stockDTO.getCantidad() < diferencia) {
                        throw new StockException();
                    }
                }
                stockAPI.editStock(new StockPatchDTO(idProducto,stockDTO.getCantidad()+cantidadAnterior-cantidad));
            }
            productoCantidadList.add(pc);
        }
        carrito.setProductList(productoCantidadList);
        actualizarSumaTotal(carrito);
        return carritoRepository.save(carrito);
    }

}