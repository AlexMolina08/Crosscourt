/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao;

import com.basados.seminario1.model.Tenista;
import java.util.List;

public interface TenistaDAO extends DAO<Tenista,String>{
    
    public List<Tenista> obtenerTenistasEdicion(int numEdicion) throws DAOException;
    public void eliminar(String cod , Integer edicion) throws DAOException;
    public Tenista obtener(String dni , Integer numEdicion) throws DAOException;
}
