/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao;
import com.basados.seminario1.model.Material;
import java.util.List;

public interface MaterialDAO extends DAO<Material, String>{
    // Devolver la lista de materiales que pertenecen a un cod.pedido
    public List<Material> getMaterialesPedido(String codPedido) throws DAOException;
}