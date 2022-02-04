/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.basados.seminario1.dao;

import com.basados.seminario1.model.Pedido;

/**
 *
 * Metodos que nos permiten acceder a la BD , realizar Consultas y Modificaciones
 * a la tabla de Pedido
 * 
 */

public interface PedidoDAO extends DAO<Pedido, String>{
    public void eliminar(String cod , String codProducto) throws DAOException;
}
