package com.basados.seminario1.dao;


import com.basados.seminario1.model.Stock;
import java.util.List;

/**
 *
 * Metodos que nos permiten acceder a la BD , realizar Consultas y Modificaciones
 * a la tabla de Stock
 * 
 */


public interface StockDAO extends DAO<Stock,Integer>{  
    Integer decrementarCantidad(Integer cod) throws  DAOException;
    void eliminarTodos() throws DAOException;
}
