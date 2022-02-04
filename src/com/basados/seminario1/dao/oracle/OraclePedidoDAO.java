package com.basados.seminario1.dao.oracle;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.DAOManager;
import com.basados.seminario1.dao.PedidoDAO;
import com.basados.seminario1.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
 *
 *  Operaciones de consulta y modificacion de Pedido
 * 
 * 
*/
public class OraclePedidoDAO implements PedidoDAO {

    /*
        * Sentencias SQL para realizar operaciones sobre la tabla PEDIDO
    */
    final String INSERT = "insert into pedido(cod_pedido,cif_patrocinador,tipos_materiales,cantidad,cod_material) VALUES (?,?,?,?,?)";
    final String UPDATE = "UPDATE PEDIDO SET CODCLIENTE=?,FECHA_PED=? WHERE (CODPEDIDO=cast(? as char(5)) AND CIF_PATROCINADOR=cast(? as char(9)))";
    final String DELETE = "delete from pedido where (cod_pedido=cast(? as char(5)) AND COD_material=cast(? as char(4)))";
    final String GETALL = "select cod_pedido,cod_material,cif_patrocinador,tipos_materiales,cantidad from pedido";
    final String CREATE = "";
    
    final String DROP = "DROP TABLE PEDIDO";
    final String GETONE = "select cod_pedido,cod_material,cif_patrocinador,tipos_materiales,cantidad from pedido where (cod_pedido=?)";
    private Connection conn ;
    
    public OraclePedidoDAO(Connection conn){
        this.conn = conn;
    }
    
    /*
        * Insertar un pedido
    */
    
    @Override
    public void insertar(Pedido s) throws DAOException{
        PreparedStatement stat = null;
        try{
            
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, s.getCod());
            stat.setString(2 , s.getCifPatrocinador());
            stat.setInt(3,s.getTipos());
            stat.setInt(4,s.getCantidad());
            stat.setString(5,s.getCodMaterial());
            
            stat.executeUpdate();  
            conn.commit();
            System.out.println("pedido "+s.getCod()+" insertado");
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
    public void modificar(Pedido s) throws DAOException{
        PreparedStatement stat = null;
        try{
            
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, s.getCod());
            stat.setString(2 , s.getCifPatrocinador());
            stat.setInt(3,s.getTipos());
            stat.setInt(4,s.getCantidad());
              
            stat.executeUpdate();  
            System.out.println("pedido "+s.getCod()+" MODIFICADO");
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
    
    public void eliminar(String cod ) throws DAOException{

    }
    
    // TODO : MODIFICAR LA INTERFAZ PARA METODO ELIMINAR @Override
    public void eliminar(String cod , String codProducto) throws DAOException{
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, cod); 
            stat.setString(2,codProducto);
            stat.execute(); 
            conn.commit();
            System.out.println("Pedido "+cod+" se elimina producto "+codProducto);
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

    /*
        * Devolver un objeto de tipo Pedido a partir de un ResultSet
    */
    
    private Pedido convertir(ResultSet rs ) throws SQLException{
        String codPedido = rs.getString("cod_pedido"); 
        String cifPatrocinador = rs.getString("cif_patrocinador");
        Integer tipos_mat = rs.getInt("tipos_materiales");
        Integer cantidad = rs.getInt("cantidad");
        String codMaterial = rs.getString("cod_material");
        
        // Creamos un nuevo pedido y lo devolvemos
        Pedido pedido = new Pedido(codPedido , codMaterial , cifPatrocinador , tipos_mat,cantidad);
        
        return pedido;
        
    }
    
    @Override
    public List<Pedido> obtenerTodos() throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        List<Pedido> pedidos = new ArrayList<Pedido>(); // lista que almacena los productos 
        
        try{
            stat = conn.prepareStatement(GETALL , ResultSet.TYPE_SCROLL_INSENSITIVE , 
                ResultSet.CONCUR_UPDATABLE 
            ) ;
            // extraer el cursor de la base de datos 
            rs = stat.executeQuery();
            
            // mientras el cursor de rs no llegue al final , extraer el 
            // pedido
            while(rs.next()){
                pedidos.add(convertir(rs));
            }//si no
            
        }catch(SQLException e){
            throw new DAOException("Error en SQL" , e);
        }finally{
            
            //Cerrar el resultset
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                   throw new DAOException("Error en SQL" , e);
                }
            }
            
            //Cerrar el preparedStatement
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException e){
                    throw new DAOException("Error en SQL" , e);
                }
            }
            
        }
        
        return pedidos;
    }

    @Override
    public Pedido obtener(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Pedido s = null;
        
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setString(1, id);
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
    public void eliminarTabla() throws DAOException{
        try {
            PreparedStatement stat = conn.prepareStatement(DROP);
            stat.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException("Error al hacer DROP a la tabla de Stock: ",e);
        }
        
    }

    @Override
    public void crearTabla() throws DAOException{
        
        try {
            PreparedStatement stat = conn.prepareStatement(CREATE);
            stat.execute();
        } catch (SQLException e) {
            throw new DAOException("Error al crear la Tabla de Stock",e);
        }
        
    }  
}
