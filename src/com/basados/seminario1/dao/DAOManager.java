package com.basados.seminario1.dao;

/**
 *
 * DAOManager permite acceder a las instancias de los DAOS de Stock , Pedido 
 * y Detalle-Pedido de una forma centralizada
 * 
 */
public interface DAOManager {
        
    PedidoDAO getPedidoDAO();
    
    PatrocinadorDAO getPatrocinadorDAO();
    
    ArbitroDAO getArbitroDAO();
    
    OfertaDAO getOfertaDAO();
    
    TenistaDAO getTenistaDAO();
    
    PartidoDAO getPartidoDAO();
    
    MaterialDAO getMaterialDAO();
}
