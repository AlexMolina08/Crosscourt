/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.tableModels;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.PedidoDAO;
import com.basados.seminario1.model.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alex
 */
public class PedidosTableModel extends AbstractTableModel {

    private PedidoDAO pedidos; // Manager para obtener los pedidos de la Base de datos
    private List<Pedido> datos = new ArrayList<>();  // Buffer donde almacenar la lista de pedidos 
    
    /**
     * 
     * Constructor para inicializar la tabla con el manager de pedidos
     * 
     * @param pedidos
     */
    
    
    
    public PedidosTableModel(PedidoDAO pedidos) {
        this.pedidos = pedidos;
    }

    
    
    @Override
    public int getRowCount() {
        return datos.size();
    }
    
    /**
     * 
     * Actualizar tabla llamando a obtener todos 
     * 
     */
    
    public void updateModel() throws DAOException{
        datos = pedidos.obtenerTodos();
        this.fireTableDataChanged(); 
    }
    
    
    @Override
    public String getColumnName(int column) {
        switch(column) {
        case 0 : return "Cod. Pedido";
        case 1 : return "Cod. Material";
        case 2 : return "CIF proveedor";
        case 3 : return "Tipos de Materiales";
        case 4 : return "Cantidad de Materiales";
        default: return "[no]";
       }
    }
    

    @Override
    public int getColumnCount() {
        return 5;
    }

    /**
     * Devolver pedido seleccionado en la jTable
     * @param rowIndex
     * @param columnIndex
     * @return 
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pedido selected = datos.get(rowIndex);
        switch(columnIndex){
           case 0 : return selected.getCod();
           case 1 : return selected.getCodMaterial();
           case 2 : return selected.getCifPatrocinador();
           case 3 : return selected.getTipos();
           case 4 : return selected.getCantidad();
           default: return "[no]";
       }
    }
    
}
