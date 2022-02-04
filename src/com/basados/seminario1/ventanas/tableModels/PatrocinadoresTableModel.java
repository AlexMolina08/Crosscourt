package com.basados.seminario1.ventanas.tableModels;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.PatrocinadorDAO;
import com.basados.seminario1.model.Patrocinador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 
 *  Modelo de la tabla con la info de los patrocinadores 
 */
public class PatrocinadoresTableModel extends AbstractTableModel {

    
    private PatrocinadorDAO patrocinadores;
    private List<Patrocinador> datos = new ArrayList<>(); // buffer datos consultados 
 
    public PatrocinadoresTableModel(PatrocinadorDAO patrocinadores) {
        this.patrocinadores = patrocinadores;
    }    

    @Override
    public String getColumnName(int column) {
        switch(column) {
        case 0 : return "CIF";
        case 1 : return "Contacto";
        case 2 : return "Teléfono";
        case 3 : return "email";
        case 4 : return "Total aportado (€)";
        case 5 : return "Edicion";
        default: return "[no]";
       }
    }
    /*
        * Actualizar los datos de la tabla
    */
    public void updateModel() throws DAOException{
        datos = patrocinadores.obtenerTodos();
    }

    /*obtener num de filas*/
    @Override
    public int getRowCount() {
        return datos.size();
    }
    /*obtener num de columnas*/
    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Patrocinador selected = datos.get(rowIndex);
        switch(columnIndex){
            case 0: return  selected.getCif();
            case 1: return  selected.getContacto();
            case 2: return  selected.getTlf();
            case 3: return  selected.getEmail();
            case 4: return  selected.getDinero_aportado();
            case 5: return  selected.getNum_edicion();
            default: return "[no]";
        }
    }
    
   
    
    
}
