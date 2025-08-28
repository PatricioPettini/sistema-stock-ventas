package com.patomicroservicios.carrito_service.service;

import com.patomicroservicios.carrito_service.Exceptions.CarritoNoEncontradoException;
import com.patomicroservicios.carrito_service.Exceptions.ProductoInactivoException;
import com.patomicroservicios.carrito_service.Exceptions.ProductoNoEncontradoException;
import com.patomicroservicios.carrito_service.Exceptions.StockException;
import com.patomicroservicios.carrito_service.dto.CarritoDTO;
import com.patomicroservicios.carrito_service.dto.ProductoDTO;
import com.patomicroservicios.carrito_service.dto.response.StockDTO;
import com.patomicroservicios.carrito_service.model.Carrito;
import com.patomicroservicios.carrito_service.model.ProductoCantidad;
import com.patomicroservicios.carrito_service.repository.ICarritoRepository;
import com.patomicroservicios.carrito_service.repository.ProductoAPI;
import com.patomicroservicios.carrito_service.repository.stockAPI;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarritoService implements ICarritoService{

    @Autowired
    ICarritoRepository carritoRepository;

    @Autowired
    ProductoAPI productoAPI;

    @Autowired
    stockAPI stockAPI;

    @Autowired
    ModelMapper modelMapper;

    //agregar producto al carrito
    @Override
    public CarritoDTO agregarProducto(Long idCarrito, Long idProducto, int cantidad) {
        Carrito carrito = carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        ProductoDTO productoDTO = productoAPI.getProducto(idProducto);
        if (productoDTO == null) throw new ProductoNoEncontradoException();
        StockDTO stockDTO = validarStock(idProducto, cantidad);
        ProductoCantidad productoCantidad=new ProductoCantidad(idProducto, cantidad);
        stockAPI.editStock(idProducto,stockDTO.getCantidad()-cantidad);
        carrito.getProductList().add(productoCantidad);
        carrito=actualizarSumaTotal(carrito);
        carritoRepository.save(carrito);
        return modelMapper.map(carrito,CarritoDTO.class);
    }

    //validar stock disponible
    private StockDTO validarStock(Long idProducto, int cantidad) {
        StockDTO stockDTO=stockAPI.getCantidadStockPoducto(idProducto);
        if(cantidad >stockDTO.getCantidad()) throw new StockException();
        ProductoDTO productoDTO=productoAPI.getProducto(idProducto);
        if(productoDTO.getEstado().equals("INACTIVO")) throw new ProductoInactivoException();
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
            stockAPI.editStock(idProducto, cantidad + stockDTO.getCantidad());

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
    public void altaCarrito() {
        Carrito carrito= new Carrito();
        carritoRepository.save(carrito);
    }

    //eliminar carrito
    @Override
    public void eliminarCarrito(Long idCarrito) {
        Carrito carrito=carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        carritoRepository.deleteById(idCarrito);
    }

    //editar la cantidad de un producto en el carrito
    @Override
    public CarritoDTO editarCantidadProducto(Long idCarrito, Long idProducto, int cantidad) {
        Carrito carrito=carritoRepository.findById(idCarrito).orElseThrow(CarritoNoEncontradoException::new);
        ProductoDTO productoDTO=productoAPI.getProducto(idProducto);
        if(productoDTO.getEstado().equals("INACTIVO")) throw new ProductoInactivoException();
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
                stockAPI.editStock(idProducto,stockDTO.getCantidad()+cantidadAnterior-cantidad);
            }
            productoCantidadList.add(pc);
        }
        carrito.setProductList(productoCantidadList);
        actualizarSumaTotal(carrito);
        carritoRepository.save(carrito);
        return modelMapper.map(carrito,CarritoDTO.class);
    }
}