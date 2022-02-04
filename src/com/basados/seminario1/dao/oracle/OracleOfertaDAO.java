/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao.oracle;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.OfertaDAO;
import com.basados.seminario1.model.Oferta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OracleOfertaDAO implements OfertaDAO{
/*
        * Sentencias SQL para realizar operaciones sobre la tabla Patrocinador
    */
    final String INSERT = "insert into oferta(cod_oferta,dni_arbitro,num_edicion,fecha,cantidad,aceptada) values (?,?,?,TO_DATE(?,'dd/mm/yy'),?,?)";
    final String DELETE = "delete from oferta where (cod_oferta=cast(? as char(5)))";
    final String DEL_ALL = "delete from oferta";
    final String GETALL = "select cod_oferta,dni_arbitro,num_edicion,fecha,cantidad,aceptada from oferta";
    final String GETONE = "select cod_oferta,dni_arbitro,num_edicion,fecha,cantidad,aceptada from oferta where cod_oferta=?";
    
    
    private Connection conn ; // crear objeto connection para entrar en la BD

    public OracleOfertaDAO(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void insertar(Oferta s) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(INSERT);
            // establecemos los valores de la consulta SQL con los datos del
            // objeto Patrocinador
            stat.setString(1, s.getCod_oferta());
            stat.setString(2 , s.getDniArbitro());  
            stat.setInt(3 , s.getNum_edicion());
            stat.setString(4 , s.getFecha());
            stat.setFloat(5, (float) s.getCantidad());
            stat.setInt(6, s.isAceptada());
                    
            stat.executeUpdate(); 
            conn.commit();
            System.out.println("Oferta "+s.getCod_oferta()+" registrada");
        } 
        
        // si no se ha podido realizar la inserción por no existir el pedido o el producto
        // asociados al identificador , lanza una excepcion
        catch(SQLException e) {
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
    public void modificar(Oferta s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(String s) throws DAOException {
        PreparedStatement stat = null;
        
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, s);
            stat.execute();
            System.out.println("oferta con codigo "+s+" eliminada");
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
    public List<Oferta> obtenerTodos() throws DAOException {

        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Oferta> ofertas = new ArrayList<>(); // lista que almacena las ofertas 
        
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
                ofertas.add(convertir(rs));
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
        
        return ofertas;

    }

    @Override
    public Oferta obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Oferta s = null;
        
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
                throw new DAOException("No se ha encontrado esa oferta");
            
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

    private Oferta convertir(ResultSet rs) throws SQLException {
        String cod_oferta = rs.getString("cod_oferta");
        String dni_arbitro = rs.getString("dni_arbitro");
        Integer num_edicion = rs.getInt("num_edicion");
        Date fecha = rs.getDate("fecha");
        String fechaS = fecha.toString();
        double cantidad = rs.getDouble("cantidad");
        int aceptada = rs.getInt("aceptada");
        
        Oferta oferta = new Oferta(cod_oferta, dni_arbitro, num_edicion , fechaS, cantidad , aceptada);
        
        return oferta;
    }
 
    
}
