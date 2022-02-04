/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.tableModels;
import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.MaterialDAO;
import com.basados.seminario1.model.Material;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MaterialesTableModel extends AbstractTableModel{
    private MaterialDAO materiales ;
    private List<Material> datos = new ArrayList<>();

    public MaterialesTableModel(MaterialDAO materiales) {
        this.materiales = materiales;
    }

    public void updateModel() throws DAOException{
        datos = materiales.obtenerTodos();
    }
    
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Cod. Material";
            case 1: return "CIF PATROCINADOR";
            case 2: return "Nombre del Material";
            case 3: return "Cantidad";
            default: return "[no]";
        }
    }

    
    
    
    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Material selected = datos.get(rowIndex);
        switch (columnIndex) {
            case 0: return selected.getCodMaterial();
            case 1: return selected.getCifPatrocinador();
            case 2: return selected.getNombre();
            case 3: return selected.getCantidad();
            default: return "[no]";
        }  
    }
}
