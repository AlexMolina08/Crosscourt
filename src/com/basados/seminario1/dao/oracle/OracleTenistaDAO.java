/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao.oracle;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.DAOManager;
import com.basados.seminario1.dao.TenistaDAO;
import com.basados.seminario1.model.Tenista;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.basados.seminario1.model.Pedido;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class OracleTenistaDAO implements TenistaDAO{

    final String INSERT = "insert into tenista(dni,nombre,apellidos,num_edicion,posicion_ranking) values (?,?,?,?,?)";
    final String GETALL = "select dni,nombre,apellidos,num_edicion,posicion_ranking from tenista";
    final String GETONE = "select dni,nombre,apellidos,num_edicion,posicion_ranking from tenista where DNI = cast(? as char(9))";
    final String GETONE_EDITION = "select dni,nombre,apellidos,num_edicion,posicion_ranking from tenista where DNI = cast(? as char(9)) and num_edicion=?";
    final String GETALL_EDITION = "select dni,nombre,apellidos,num_edicion,posicion_ranking from tenista where num_edicion=? order by posicion_ranking";
    final String DELETE = "delete from tenista  where (DNI = cast(? as char(9)) AND num_edicion=?)";
    
    private Connection conn;

    public OracleTenistaDAO(Connection conn) {
        this.conn = conn;
    }
    
    
    @Override
    public void insertar(Tenista s) throws DAOException {
        PreparedStatement stat = null;
        try{
            
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, s.getDni());
            stat.setString(2 , s.getNombre());
            stat.setString(3,s.getApellidos());
            stat.setInt(4,s.getNumEdicion());
            stat.setInt(5,s.getPosicionRanking());
            
            stat.executeUpdate();  
            conn.commit();
            System.out.println("TENISTA "+s.getDni()+" insertado");
        } catch(SQLException e) {
            throw new DAOException("Error en SQL: " + e.getMessage(), e);
        }finally{
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    throw new DAOException("Error en SQL: "+e.getMessage(),e);
                }
            }
        } 
    }

    @Override
    public void modificar(Tenista s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    public void eliminar(String cod , Integer edicion) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, cod); 
            stat.setInt(2, edicion);
            stat.execute(); 
            conn.commit();
            System.out.println("Tenista "+cod+" eliminado de la edicion "+edicion);
        } catch(SQLException e) {
            throw new DAOException("Error en SQL" , e);
        }finally{
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    throw new DAOException("Error en SQL",e);
                }
            }
        }
    }

    /**
     * 
     * @param numEdicion
     * @return Lista con los tenistas de una edicion
     */
    @Override
    public List<Tenista> obtenerTenistasEdicion(int numEdicion) throws DAOException{

        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Tenista> tenistas = new ArrayList<Tenista>(); // lista que almacena los productos 
        
        try{
            stat = conn.prepareStatement(GETALL_EDITION , ResultSet.TYPE_SCROLL_INSENSITIVE , 
                ResultSet.CONCUR_UPDATABLE 
            ) ;
            
            stat.setInt(1, numEdicion);
            
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // mientras el cursor de rs no llegue al final , extraer el 
            // pedido
            while(rs.next()){
                tenistas.add(convertir(rs));
            }//si no
            
            }catch(SQLException e){
                throw new DAOException("Error en SQL "+ e.getMessage() , e);
            }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                   throw new DAOException("Error en SQL " , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    throw new DAOException("Error en SQL " + e.getMessage() , e);
                }
            }
            
        }
        
        return tenistas;
    }
    
    @Override
    public List<Tenista> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Tenista> tenistas = new ArrayList<Tenista>(); // lista que almacena los productos 
        
        try{
            stat = conn.prepareStatement(GETALL , ResultSet.TYPE_SCROLL_INSENSITIVE , 
                ResultSet.CONCUR_UPDATABLE 
            ) ;
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // mientras el cursor de rs no llegue al final , extraer el 
            // pedido
            while(rs.next()){
                tenistas.add(convertir(rs));
            }//si no
            
            }catch(SQLException e){
                throw new DAOException("Error en SQL" + e.getMessage() , e);
            }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                   throw new DAOException("Error en SQL"  + e.getMessage(), e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    throw new DAOException("Error en SQL" + e.getMessage() , e);
                }
            }
            
        }
        
        return tenistas;
    }
    
    /*
        * Devolver un objeto de tipo Tenista a partir de un ResultSet
    */
    
    private Tenista convertir(ResultSet rs ) throws SQLException{
        String dni = rs.getString("dni"); 
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        Integer numEdicion = rs.getInt("num_edicion");
        Integer posRanking = rs.getInt("posicion_ranking");
        
        // Creamos un nuevo pedido y lo devolvemos
        Tenista tenista = new Tenista(dni, nombre, apellidos, numEdicion, posRanking);
        
        return tenista;
        
    }

    @Override
    public Tenista obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Tenista s = null;
        
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setString(1,id);
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // si se ha obtenido un resultado (rs.next())
            // se convirse el resultset en un objeto Pedido
            if(rs.next()){
                s = convertir(rs);
            }//si no
            else
                throw new DAOException("No se ha encontrado ese registro");
            
            }catch(SQLException e){
                throw new DAOException("Error en SQL" + e.getMessage() , e);
            }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" + e.getMessage() , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" + e.getMessage() , e);
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
    

    @Override
    public void eliminar(String s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Tenista obtener(String dni, Integer numEdicion) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Tenista s = null;
        
        try{
            stat = conn.prepareStatement(GETONE_EDITION);
            stat.setString(1,dni);
            stat.setInt(2,numEdicion);
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // si se ha obtenido un resultado (rs.next())
            // se convirse el resultset en un objeto Pedido
            if(rs.next()){
                s = convertir(rs);
            }//si no
            else
                throw new DAOException("No se ha encontrado ese registro");
            
            }catch(SQLException e){
                throw new DAOException("Error en SQL" + e , e);
            }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" + e.getMessage() , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    new DAOException("Error en SQL" + e.getMessage() , e);
                }
            }
            
        }
        
        return s;
    }
    
}