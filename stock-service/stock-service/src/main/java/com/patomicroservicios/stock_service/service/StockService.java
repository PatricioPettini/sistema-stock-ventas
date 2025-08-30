package com.patomicroservicios.stock_service.service;

import com.patomicroservicios.stock_service.Exceptions.ProductoNoEncontradoException;
import com.patomicroservicios.stock_service.Exceptions.StockNoRegistradoException;
import com.patomicroservicios.stock_service.Exceptions.StockRegistradoException;
import com.patomicroservicios.stock_service.dto.request.StockCreateDTO;
import com.patomicroservicios.stock_service.dto.response.ProductoDTO;
import com.patomicroservicios.stock_service.dto.response.StockDTO;
import com.patomicroservicios.stock_service.model.Stock;
import com.patomicroservicios.stock_service.repository.IStockRepository;
import com.patomicroservicios.stock_service.repository.productoAPI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService implements IStockService{

    @Autowired
    IStockRepository stockRepository;

    @Autowired
    productoAPI productoAPI;

    @Autowired
    ModelMapper modelMapper;

    //registrar stock de un producto
    @Override
    public StockDTO addStock(StockCreateDTO st) {
        Stock stock=stockRepository.getProductStock(st.getIdProducto());
        if(stock!=null) throw new StockRegistradoException(st.getIdProducto());
        stock= new Stock();
        stock.setIdProducto(st.getIdProducto());
        stock.setCantidad(st.getCantidad());
//        stock.setFechaActualizacion(LocalDate.now());
//        stock.setCreadoEn(LocalDate.now());
        Stock stockGuardado=stockRepository.save(stock);
        return modelMapper.map(stockGuardado,StockDTO.class);
    }

    //editar la cantidad de stock de un producto
    @Override
    public StockDTO editStock(Long idProducto, int nuevaCantidad) {
        if (nuevaCantidad < 0) throw new IllegalArgumentException("Cantidad negativa");
        StockDTO stockDto=getStock(idProducto);
        stockDto.setCantidad(nuevaCantidad);
//        stock.setFechaActualizacion(LocalDate.now());
        Stock st=modelMapper.map(stockDto,Stock.class);
        return toDto(stockRepository.save(st));
    }

    //obtener el stock de un producto determinado
    @Override
    public StockDTO getStock(Long idProducto) {
        ProductoDTO productoDTO = productoAPI.getProducto(idProducto);
        if(productoDTO==null) throw new ProductoNoEncontradoException(idProducto);
        Stock stock=stockRepository.getProductStock(idProducto);
        if(stock==null) throw new StockNoRegistradoException(idProducto);
        return toDto(stock);
    }

    public StockDTO toDto(Stock stock){
        return modelMapper.map(stock,StockDTO.class);
    }

    @Override
    public List<StockDTO> getAllStock() {
        return stockRepository.findAll().stream()
                .map(this::toDto)   // acá usamos ModelMapper
                .collect(Collectors.toList());
    }
}
