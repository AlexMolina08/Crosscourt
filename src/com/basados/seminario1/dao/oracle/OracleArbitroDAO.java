package com.basados.seminario1.dao.oracle;

/**
 * 
 * Clase con toda la funcionalidad para obtener datos del servidor utilizando
 * JDBC 
 * 
 * @author Alex
 */
/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
import com.basados.seminario1.dao.ArbitroDAO;
import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.model.Arbitro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
    * Clase con toda la funcionalidad para manipular la tabla de 
 * patrocinador
 */
public class OracleArbitroDAO implements ArbitroDAO{
    /*
        * Sentencias SQL para realizar operaciones sobre la tabla Patrocinador
    */
    final String INSERT = "insert into arbitro(dni,nombre,apellidos,correo,num_edicion) values(?,?,?,?,?)";
    final String DELETE = "";
    final String DEL_ALL = "delete from arbitro";
    final String GETALL = "select dni,nombre,apellidos,correo,num_edicion from arbitro";
    final String GETONE = "select dni,nombre,apellidos,correo,num_edicion from arbitro where dni=?";

    final String CREATE = "";
    
    final String DROP = "drop table arbitro";
    
    
    private Connection conn ; // crear objeto connection para entrar en la BD

    public OracleArbitroDAO(Connection conn) {
        this.conn = conn;
    }
    
    /*
        * Convertir un resulset de una consulta sql al modelo Arbitro
    */
    private Arbitro convertir(ResultSet rs) throws SQLException{
        String dni = rs.getString("dni");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        String correo = rs.getString("correo");
        int num_edicion = rs.getInt("num_edicion");
        
        Arbitro arbitro = new Arbitro(dni, nombre, apellidos , correo , num_edicion);
        
        return arbitro;
        
    }
    
    /*
        * Insertar un patrocinador
    */

    @Override
    public void modificar(Arbitro s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(String id) throws DAOException {
        
        PreparedStatement stat = null;
        
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, id);
            stat.execute();
            System.out.println("patrocinador con CIF "+id+" eliminado");
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
    public void eliminarTabla() throws DAOException {
        try {
            PreparedStatement stat = conn.prepareStatement(DROP);
            stat.execute();
        } catch (SQLException e) {
            throw new DAOException("Error al hacer DROP a la tabla de Stock: ",e);
        }
    }

    /*
        * Creación de la tabla de patrocinador
    */
    @Override
    public void crearTabla() throws DAOException {
        try {
            PreparedStatement stat = conn.prepareStatement(CREATE);
            stat.execute();
        } catch (SQLException e) {
            throw new DAOException("Error al crear la Tabla Patrocinador",e);
        }
    }
    
    
  

    @Override
    public List<Arbitro> obtenerTodos() throws DAOException {
        
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Arbitro> arbitros = new ArrayList<>(); // lista que almacena los arbitros 
        
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
                arbitros.add(convertir(rs));
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
        
        System.out.println(arbitros);
        return arbitros;
    }

    @Override
    public Arbitro obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Arbitro s = null;
        
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
    public void insertar(Arbitro s) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}

