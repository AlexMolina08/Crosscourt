/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.tableModels;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.TenistaDAO;
import com.basados.seminario1.model.Tenista;
/**
 *
 * @author Alex
 */
public class TenistasTableModel extends AbstractTableModel{

    private TenistaDAO tenistas;
    private List<Tenista> datos = new ArrayList<>(); // buffer datos consultados 
 
    public TenistasTableModel(TenistaDAO tenistas) {
        this.tenistas = tenistas;
    }    

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0 : return "DNI";
            case 1 : return "Nombre";
            case 2 : return "Apellido(s)";
            case 3 : return "Nº de Edición";
            case 4 : return "Ranking";
            default: return "[no]";
       }
    }

    public void updateModel(int numEdicion) throws DAOException{
        datos = tenistas.obtenerTenistasEdicion(numEdicion);
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
    
        Tenista selected = datos.get(rowIndex);
        
        switch(columnIndex) {
            case 0 : return selected.getDni();
            case 1 : return selected.getNombre();
            case 2 : return selected.getApellidos();
            case 3 : return selected.getNumEdicion();
            case 4 : return selected.getPosicionRanking();
            default: return "[no]";
       }
    }
    
}
