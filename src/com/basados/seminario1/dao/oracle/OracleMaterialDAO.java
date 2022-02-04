/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao.oracle;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.DAOManager;
import com.basados.seminario1.dao.MaterialDAO;
import com.basados.seminario1.model.Material;
import com.basados.seminario1.model.Patrocinador;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class OracleMaterialDAO implements MaterialDAO {
    
    private Connection conn;
    
    final String GETALL = "select cod_material,nombre,cantidad,cif_patrocinador from material";
    final String GETONE = "select cod_material,nombre,cantidad,cif_patrocinador from material where(cod_material=?)";
    
    //final String GETALL_PEDIDO = 

    public OracleMaterialDAO(Connection conn) {
        this.conn = conn;
    }

    
    
    @Override
    public List<Material> getMaterialesPedido(String codPedido) throws DAOException{
        List<Material> materiales = new ArrayList<>();
        
        PreparedStatement stat = null;
        ResultSet rs = null;
                
        try{
            stat = conn.prepareStatement(GETALL , ResultSet.TYPE_SCROLL_INSENSITIVE , 
                ResultSet.CONCUR_UPDATABLE 
            ) ;
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // rs ahora apunta a la primera columna de la tabla
            
            // mientras el cursor de rs no llegue al final , extraer el 
            // patrocinador correspondiente 
            while(rs.next()){
                materiales.add(convertir(rs));
            }
            
        }catch(SQLException e){
            throw new DAOException("Error en SQL" , e);
            
        }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" , e);
                }
            }
            
        }
        
        System.out.println(materiales);
        return materiales;
    }

    @Override
    public void insertar(Material s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modificar(Material s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(String s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Material> obtenerTodos() throws DAOException {
        
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Material> materiales = new ArrayList<>(); // lista que almacena los materiales 
        
        try{
            stat = conn.prepareStatement(GETALL , ResultSet.TYPE_SCROLL_INSENSITIVE , 
                ResultSet.CONCUR_UPDATABLE 
            ) ;
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // rs ahora apunta a la primera columna de la tabla
            
            // mientras el cursor de rs no llegue al final , extraer el 
            // patrocinador correspondiente 
            while(rs.next()){
                materiales.add(convertir(rs));
            }
            
        }catch(SQLException e){
            throw new DAOException("Error en SQL" , e);
            
        }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" , e);
                }
            }
            
        }
        
        System.out.println(materiales);
        return materiales;    
    }

    @Override
    public Material obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Material s = null;
        
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setString(1, id);
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // si se ha obtenido un resultado (rs.next())
            // se convirse el resultset en un objeto Stock
            if(rs.next()){
                s = convertir(rs);
            }//si no
            else
                throw new DAOException("No se ha encontrado ese registro");
            
        }catch(SQLException e){
            throw new DAOException("Error en SQL" , e);
        }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" , e);
                }
            }
            
        }
        
        return s;
    }

    @Override
    public void eliminarTabla() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void crearTabla() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Material convertir(ResultSet rs) throws SQLException {
        String codMaterial = rs.getString("cod_material");
        String nombre      = rs.getString("nombre");
        Integer cantidad    = rs.getInt("cantidad");
        String cifPatrocinador  = rs.getString("cif_patrocinador");
        
        return new Material(codMaterial, nombre, cantidad , cifPatrocinador);
        
    }
    
}
