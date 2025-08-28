package com.patomicroservicios.stock_service.service;

import com.patomicroservicios.stock_service.dto.request.StockCreateDTO;
import com.patomicroservicios.stock_service.dto.response.StockDTO;
import com.patomicroservicios.stock_service.model.Stock;

import java.util.List;

public interface IStockService {
    StockDTO addStock(StockCreateDTO stock);
    StockDTO editStock(Long idProducto, int cantidad);
    Stock getStock(Long idProducto);
    StockDTO getStockDTO(Long idProducto);
    List<Stock> getAllStock();
}
