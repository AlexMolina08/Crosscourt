/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.tableModels;

import com.basados.seminario1.dao.ArbitroDAO;
import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.model.Arbitro;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alex
 */
public class ArbitrosTableModel extends AbstractTableModel{
  
    
    private ArbitroDAO arbitros;
    private List<Arbitro> datos = new ArrayList<>();

    public ArbitrosTableModel(ArbitroDAO arbitros) {
        this.arbitros = arbitros;
    }
  
    
    
    public void updateModel() throws DAOException{
        datos = arbitros.obtenerTodos();
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column) {
        case 0 : return "DNI";
        case 1 : return "Nombre";
        case 2 : return "Apellidos";
        case 3 : return "Correo";
        case 4 : return "Num. Edición";
        default: return "[no]";
       }
    }

    
    
    
    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Arbitro selected = datos.get(rowIndex);
        switch(columnIndex) {
            case 0 : return selected.getDni();
            case 1 : return selected.getNombre();
            case 2 : return selected.getApellidos();
            case 3 : return selected.getCorreo();
            case 4 : return selected.getNumEdicion();
            default: return "[no]";
       }
    }
    
}
