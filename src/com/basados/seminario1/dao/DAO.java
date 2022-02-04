package com.basados.seminario1.dao;

import java.util.List;

/**
 *
 * 
 * Metodos que nos permiten acceder a la BD , realizar Consultas y Modificaciones
 * Se ha declarado una interfaz en lugar de una clase para separar la funcionalidad
 * del SGBD de la lógica de la aplicación
 * De esta forma , si en algun momento se cambia de SGBD solo habra que crear las
 * clases correspondientes que implementen esta interfaz
 * 
 * En nuestro caso , el SGBD es oracle, (la implementación están en el paquete dao.oracle)
 * 
 * T Modelo (Stock , Pedido , DetallePedido)
 * K(tipo de dato de la clave)
 * 
 */

public interface DAO<T,K> {
    void insertar(T s) throws DAOException;
    
    void modificar(T s) throws DAOException;
    
    void eliminar(K s) throws DAOException;
    
    List<T> obtenerTodos() throws DAOException;
    
    /*
        * Devolver alumno a partir de identificador del tipo k
    */
    T obtener(K id) throws DAOException;
    
    void eliminarTabla() throws DAOException;
    void crearTabla() throws DAOException;
    
}