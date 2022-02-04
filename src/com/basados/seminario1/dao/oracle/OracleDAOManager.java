package com.basados.seminario1.dao.oracle;


import com.basados.seminario1.dao.ArbitroDAO;
import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.DAOManager;
import com.basados.seminario1.dao.MaterialDAO;
import com.basados.seminario1.dao.OfertaDAO;
import com.basados.seminario1.dao.PartidoDAO;
import com.basados.seminario1.dao.PatrocinadorDAO;
import com.basados.seminario1.dao.PedidoDAO;
import com.basados.seminario1.dao.StockDAO;
import com.basados.seminario1.dao.TenistaDAO;
import com.basados.seminario1.dao.oracle.OracleMaterialDAO;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * Implementación del DAOManager
 * Solo tiene sentido una instancia de esta clase (Patron singleton)
 * 
 */
public class OracleDAOManager implements DAOManager{
    
    // Objeto Connection que establece la conexion
    private static Connection conn = null;
    
    // -- DATOS PARA ACCEDER AL SERVIDOR DE LA UGR
    private final static String nombreServidor = "oracle0.ugr.es";
    private final static String numeroPuerto = "1521";
    private final static String sid = "practbd.oracle0.ugr.es";
    private final static  String //URL "jdbc:oracle:thin:@nombreServidor:numeroPuerto:SID"
        url = "jdbc:oracle:thin:@" + nombreServidor + ":" + numeroPuerto + "/" + sid; 

    // --
    
    // instancia de los daos de productos , pedidos y tetalles
    private  MaterialDAO materiales = null;
    private  PedidoDAO pedidos = null;
    private  PatrocinadorDAO patrocinadores = null;
    private  ArbitroDAO arbitros = null;
    private  OfertaDAO ofertas = null;
    private  TenistaDAO tenistas = null;
    private  PartidoDAO partidos = null;
    
    
    public OracleDAOManager(String usuario , String password) throws SQLException{
        
        //Se carga el driver JDBC
        DriverManager.registerDriver( new oracle.jdbc.driver.OracleDriver() );  
        //Obtiene la conexion
        conn = DriverManager.getConnection( url, usuario, password );
        // Desactivamos el Autocommit 
        conn.setAutoCommit(false);
        
        System.out.print("DAOManager : Conexion establecida");
        
    }
    
    /*
        * Una transaccion inicia estableciendo el auto commit en false
    */
    public void iniciarTransaccion() throws DAOException{
        
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DAOException("Error al INICIAR transaccion");
        }
    }
    
    public void commitTransaccion() throws DAOException{
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new DAOException("Error al hacer COMMIT en transaccion");
        }
        
    }
    
    public void rollbackTransaccion() throws DAOException{
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new DAOException("Error al hacer ROLLBACK en transaccion");
        }
    }
    
    public void close () throws DAOException{
        try {
            conn.close();
        } catch (SQLException ex) {
            throw new DAOException("Error al hacer CERRAR la transaccion");
        }
    }
    

    // Obtener DAOS 
    

    


    /*
        * Obtener unica instancia DAO PEDIDOS , inicializar si aun no lo esta
    */
    @Override
    public PedidoDAO getPedidoDAO() {
       if(pedidos == null)
           pedidos = new OraclePedidoDAO(conn);
       System.out.println("Pedido obtenido");
       
       return pedidos;
    }


    
    
    
// -------------------------

    @Override
    public PatrocinadorDAO getPatrocinadorDAO() {
        if(patrocinadores == null)
           patrocinadores = new OraclePatrocinadorDAO(conn);
       return patrocinadores;
    }

    @Override
    public ArbitroDAO getArbitroDAO() {
        if(arbitros == null)
           arbitros = new OracleArbitroDAO(conn);
       return arbitros;
    }
    
    public OfertaDAO getOfertaDAO(){
        if(ofertas == null)
           ofertas = new OracleOfertaDAO(conn);
       return ofertas;
    }

    @Override
    public TenistaDAO getTenistaDAO() {
        if(tenistas == null)
           tenistas = new OracleTenistaDAO(conn);
       return tenistas;
    }

    @Override
    public PartidoDAO getPartidoDAO() {
        if(partidos == null)
           partidos = new OraclePartidoDAO(conn);
       return partidos;   
    }
    
    
    public MaterialDAO getMaterialDAO(){
        if(materiales == null)
           materiales = new OracleMaterialDAO(conn);
       return materiales;  
    }
    
    
}
