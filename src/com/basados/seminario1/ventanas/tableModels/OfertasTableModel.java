/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.tableModels;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.OfertaDAO;
import com.basados.seminario1.model.Oferta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alex
 */
public class OfertasTableModel extends AbstractTableModel{
    
    private OfertaDAO ofertas ;
    private List<Oferta> datos = new ArrayList<>();

    public OfertasTableModel(OfertaDAO ofertas) {
        this.ofertas = ofertas;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Cod. Oferta";
            case 1: return "DNI Árbitro";
            case 2: return "Nº Edición";
            case 3: return "Fecha";
            case 4: return "Cantidad(€)";
            case 5: return "Aceptada";
            default: return "[no]";
        }        
    }
    
    public void updateModel() throws DAOException{
        this.datos = ofertas.obtenerTodos();
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Oferta selected = datos.get(rowIndex);
        switch (columnIndex) {
            case 0: return selected.getCod_oferta();
            case 1: return selected.getDniArbitro();
            case 2: return selected.getNum_edicion();
            case 3: return selected.getFecha();
            case 4: return selected.getCantidad();
            case 5: return selected.isAceptada();
            default: return "[no]";
        }  
    }
    
}
