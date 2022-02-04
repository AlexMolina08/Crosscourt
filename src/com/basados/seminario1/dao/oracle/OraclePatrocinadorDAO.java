/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.dao.oracle;
import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.model.Patrocinador;
import com.basados.seminario1.dao.PatrocinadorDAO;
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
public class OraclePatrocinadorDAO implements PatrocinadorDAO{
    /*
        * Sentencias SQL para realizar operaciones sobre la tabla Patrocinador
    */
    final String INSERT = "insert into patrocinador(cif,contacto,tlf,email,dineroaportado,numedicion) values (?,?,?,?,?,?)";
    final String DELETE = "delete from patrocinador where (cif=?)";
    final String DEL_ALL = "delete from patrocinador";
    final String GETALL = "select cif,contacto,tlf,email,dineroaportado,numedicion from patrocinador";
    final String GETONE = "select cif,contacto,tlf,email,dineroaportado,numedicion from patrocinador where cif=?";

    final String CREATE = "create table patrocinador (" +
                        "cif char(9)," +
                        "contacto number(9)," +
                        "tlf char(9)," +
                        "email char(30)," +
                        "dineroaportado float(3),"+
                        "numedicion number(2),"+
                        "primary key(cif)"+
                        ")";
    
    final String DROP = "drop table patrocinador";
    
    
    private Connection conn ; // crear objeto connection para entrar en la BD

    public OraclePatrocinadorDAO(Connection conn) {
        this.conn = conn;
    }
    
    /*
        * Convertir un resulset de una consulta sql al modelo
    */
    private Patrocinador convertir(ResultSet rs) throws SQLException{
        String cif = rs.getString("cif");
        String contacto = rs.getString("contacto");
        String tlf = rs.getString("tlf");
        String email = rs.getString("email");
        float dinero_aportado = rs.getFloat("dineroaportado");
        System.out.println("\n"+dinero_aportado+"\n");
        int num_edicion = rs.getInt("numedicion");
        
        Patrocinador patrocinador = new Patrocinador(cif,contacto
                ,tlf,email,dinero_aportado,num_edicion);
        
        return patrocinador;
        
    }
    
    /*
        * Insertar un patrocinador
    */
    @Override
    public void insertar(Patrocinador s) throws DAOException{
               
        
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(INSERT);
            // establecemos los valores de la consulta SQL con los datos del
            // objeto Patrocinador
            stat.setString(1, s.getCif());
            stat.setString(2 , s.getContacto());  
            stat.setString(3 , s.getTlf());
            stat.setString(4 , s.getEmail());
            stat.setFloat(5,s.getDinero_aportado());
            stat.setInt(6, s.getNum_edicion());
        
                System.out.print(s.getDinero_aportado()+"\n");
            
            stat.executeUpdate(); 
            conn.commit();
            System.out.println("Patrocinador "+s.getDinero_aportado()+" registrado");
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
    public void modificar(Patrocinador s) throws DAOException {
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
    public List<Patrocinador> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Patrocinador> patrocinadores = new ArrayList<>(); // lista que almacena los patrocinadores 
        
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
                patrocinadores.add(convertir(rs));
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
        
        System.out.println(patrocinadores);
        return patrocinadores;
    }

    @Override
    public Patrocinador obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Patrocinador s = null;
        
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
}
