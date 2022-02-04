/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao.oracle;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.DAOManager;
import com.basados.seminario1.dao.PartidoDAO;
import com.basados.seminario1.model.Partido;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alex
 */
public class OraclePartidoDAO implements PartidoDAO{

    final String INSERT = "insert into partido(cod_partido,dni_tenista1,dni_tenista2,dni_arbitro,numpista,fecha) values(?,?,?,?,?,TO_DATE(?,'dd/mm/yy'))";
    final String GETALL = "select cod_partido,dni_tenista1,dni_tenista2,dni_arbitro,numpista,fecha from partido";
    final String GETONE = "select cod_partido,dni_tenista1,dni_tenista2,dni_arbitro,numpista,fecha from partido where cod_partido=cast(? as char(4))";
    final String DELETE = "delete from partido where (cod_partido=cast(? as char(4)))";
    
    private Connection conn;

    public OraclePartidoDAO(Connection conn) {
        this.conn = conn;
    }
    
    
    
    @Override
    public void insertar(Partido s) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(INSERT);
            // establecemos los valores de la consulta SQL con los datos del
            // objeto Patrocinador
            stat.setString(1, s.getCodPartido());
            stat.setString(2 , s.getDni1());  
            stat.setString(3 , s.getDni2());
            stat.setString(4 , s.getDniArbitro());
            stat.setInt(5,s.getNumPista());
            stat.setString(6, s.getFecha());
                    
            stat.executeUpdate(); 
            conn.commit();
            System.out.println("Partido "+s.getCodPartido()+" registrado");
        } 
        
        // si no se ha podido realizar la inserción por no existir el pedido o el producto
        // asociados al identificador , lanza una excepcion
        catch(SQLException e) {
            throw new DAOException("Error en SQL: " + e , e);
        }finally{
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    throw new DAOException("Error en SQL: " + e,e);
                }
            }
        }
    }

    @Override
    public void modificar(Partido s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(String s) throws DAOException {
        PreparedStatement stat = null;
        
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, s);
            stat.execute();
            System.out.println("Partido con código "+s+" eliminado");
            conn.commit();
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

    @Override
    public List<Partido> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Partido> partidos = new ArrayList<>(); // lista que almacena las ofertas 
        
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
                partidos.add(convertir(rs));
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
        
        return partidos;
    }

    @Override
    public Partido obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Partido s = null;
        
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
                throw new DAOException("No se ha encontrado ese Partido");
            
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

    private Partido convertir(ResultSet rs) throws SQLException {

        String codPartido = rs.getString("cod_partido");
        String dni1 = rs.getString("dni_tenista1");
        String dni2 = rs.getString("dni_tenista2");
        String dniArbitro = rs.getString("dni_arbitro");
        Integer numPista = rs.getInt("numpista");
        Date fecha = rs.getDate("fecha");
        String fechaS = fecha.toString();
        
        return new Partido(codPartido,dni1,dni2,dniArbitro,fechaS,numPista);
    }
   
    
}
