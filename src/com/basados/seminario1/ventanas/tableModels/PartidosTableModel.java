/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.tableModels;

import com.basados.seminario1.dao.DAOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.basados.seminario1.dao.PartidoDAO;
import com.basados.seminario1.model.Partido;


/**
 *
 * @author Alex
 */
public class PartidosTableModel extends AbstractTableModel{

    private PartidoDAO partidos;
    private List<Partido> datos = new ArrayList<>();

    public PartidosTableModel(PartidoDAO partidos) {
        this.partidos = partidos;
    }
    
    public void updateModel() throws DAOException{
        this.datos = partidos.obtenerTodos();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Cod. Partido";
            case 1: return "Jugador 1";
            case 2: return "Jugador 2";
            case 3: return "Árbitro";
            case 4: return "Nº de pista";
            case 5: return "Fecha";
            default: return "[no]";
        }   
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
        Partido selected = datos.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return selected.getCodPartido();
            case 1: return selected.getDni1();
            case 2: return selected.getDni2();
            case 3: return selected.getDniArbitro();
            case 4: return selected.getNumPista();
            case 5: return selected.getFecha();
            default: return "[no]";
        }
        
    }
    
}
