/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.main;

import com.basados.seminario1.dao.DAOException;
import com.basados.seminario1.dao.DAOManager;
import com.basados.seminario1.dao.oracle.OracleDAOManager;
import com.basados.seminario1.model.Arbitro;
import com.basados.seminario1.model.Oferta;
import com.basados.seminario1.model.Partido;
import com.basados.seminario1.model.Patrocinador;
import com.basados.seminario1.model.Pedido;
import com.basados.seminario1.model.Tenista;
import com.basados.seminario1.ventanas.tableModels.ArbitrosTableModel;
import com.basados.seminario1.ventanas.tableModels.MaterialesTableModel;
import com.basados.seminario1.ventanas.tableModels.PatrocinadoresTableModel;
import com.basados.seminario1.ventanas.tableModels.PedidosTableModel;
import com.basados.seminario1.ventanas.tableModels.OfertasTableModel;
import com.basados.seminario1.ventanas.tableModels.PartidosTableModel;
import com.basados.seminario1.ventanas.tableModels.TenistasTableModel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class MainMenu extends javax.swing.JFrame {

    
    
    private Color selectedButtonColor = new Color(194,194,194);
    private DAOManager               manager ; // Instancia manager para acceder a los DAO
    private PatrocinadoresTableModel patrocinadoresModel; // Modelo de la tabla de patrocinadores
    private PedidosTableModel        pedidosModel;   // Modelo de la tabla de pedidos
    private ArbitrosTableModel       arbitrosModel; // Modelo de la tabla de árbitros
    private OfertasTableModel        ofertasModel;  // Modelo de la tabla de ofertas registradas
    private TenistasTableModel       tenistasModel; // Modelo de la tabla de tenistas
    private PartidosTableModel       partidosModel; // Modelo de la tabla de partidos
    private MaterialesTableModel     materialesModel;    // Modelo de la tabla de Materiales
    
    
    /**
     * 
     * 
     * Al iniciar el menu , se llama a JDBC para obtener los datos de los patrocinadores que es la primera
     * ventana mostrada en la aplicación.
     *  
     * 
     * @param manager
     * @throws com.basados.seminario1.dao.DAOException
     */
    public MainMenu(DAOManager manager) throws DAOException, SQLException {
        initComponents();
        
        this.manager = manager;
        
        // Llamar a la bd para obtener los datos en los modelos de las tablas
        this.patrocinadoresModel = new PatrocinadoresTableModel(manager.getPatrocinadorDAO());
        this.pedidosModel = new PedidosTableModel(manager.getPedidoDAO());
        this.arbitrosModel = new ArbitrosTableModel(manager.getArbitroDAO());
        this.ofertasModel = new OfertasTableModel(manager.getOfertaDAO());
        this.tenistasModel = new TenistasTableModel(manager.getTenistaDAO());
        this.partidosModel = new PartidosTableModel(manager.getPartidoDAO());
        this.materialesModel = new MaterialesTableModel(manager.getMaterialDAO());
        
        this.pedidosModel.updateModel();
        this.patrocinadoresModel.updateModel();
        this.arbitrosModel.updateModel();
        this.ofertasModel.updateModel();
        this.tenistasModel.updateModel(1);
        this.partidosModel.updateModel();
        this.materialesModel.updateModel();
        
        this.jPatrocinadoresTable.setModel(patrocinadoresModel);
        this.jPedidosTable.setModel(pedidosModel);
        this.jArbitrosTable.setModel(arbitrosModel);
        this.jArbitrosOfertablesTable.setModel(arbitrosModel);
        this.jOfertasRegistradasTable.setModel(ofertasModel);
        this.jTenistaTable.setModel(tenistasModel);
        this.jPartidosTable.setModel(partidosModel);
        this.jMaterialesPedidoTable.setModel(materialesModel);
        
        // no hay ninguna fila seleccionada al inicio
        // Desactivar los botones que requieren de una selección en alguna
        // de las tablas
        eliminarButton.setEnabled(false);
        newOfferButton1.setEnabled(false);
        removeOfferButton.setEnabled(false);
        removeOfferButton.setEnabled(false);
        cancelOrderButton.setEnabled(false);
        jNuevoPartidoButton.setEnabled(false);
        
        
        // al inicio el boton de patrocinadores está seleccionado
        patrocinadoresButton.setBackground(selectedButtonColor);
        
        /** 
         ******************************************************************************************
         * Listeners para comprobar si se ha seleccionado un elemento de determinada tabla
         * y activar / desactivar un boton en consecuencia
         ******************************************************************************************
        */ 
        
        this.jPatrocinadoresTable.getSelectionModel().addListSelectionListener(e-> {
            boolean seleccionValida = (jPatrocinadoresTable.getSelectedRow() != -1);
            // botones editar y borrar se activan si se ha seleccionado una fila
            eliminarButton.setEnabled(seleccionValida);
        });
        
        this.jPedidosTable.getSelectionModel().addListSelectionListener(e->{
            boolean seleccionValida = (jPedidosTable.getSelectedRow() != -1);
            // botones editar y borrar se activan si se ha seleccionado una fila
            cancelOrderButton.setEnabled(seleccionValida);
        });
        
        this.jArbitrosOfertablesTable.getSelectionModel().addListSelectionListener(e->{
            boolean seleccionValida = (jArbitrosOfertablesTable.getSelectedRow() != -1);
            
            newOfferButton1.setEnabled(seleccionValida);
        });
        
        this.jOfertasRegistradasTable.getSelectionModel().addListSelectionListener(e->{
            boolean seleccionValida = (jOfertasRegistradasTable.getSelectedRow() != -1);
            // botones editar y borrar se activan si se ha seleccionado una fila
            removeOfferButton.setEnabled(seleccionValida);
        });
        
        this.jArbitrosTable.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionValida = (jArbitrosTable.getSelectedRow() != -1);
            // botones editar y borrar se activan si se ha seleccionado una fila
            jNuevoPartidoButton.setEnabled(seleccionValida);
        });
        
        this.jEdicionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel = (String)jEdicionBox.getSelectedItem();
                try{
                    switch (sel){
                        case "1ª Ed.": 
                            tenistasModel.updateModel(1);
                            break;
                        case "2ª Ed.":
                            tenistasModel.updateModel(2);
                            break;
                        case "3ª Ed.":
                            tenistasModel.updateModel(3);
                            break; 
                    }
                    tenistasModel.fireTableDataChanged();
                    
                }catch(DAOException ex){
                    
                }
            }
        });
       
        /** 
        ******************************************************************************************
        ******************************************************************************************
        */ 
        
    }
    
    /**
     * Obtener Partocinador seleccionado de la tabla 
     */

    private Patrocinador getPatrocinadorSeleccionado() throws DAOException{
        String cod = (String) jPatrocinadoresTable.getValueAt(jPatrocinadoresTable.getSelectedRow(), 0);
        return manager.getPatrocinadorDAO().obtener(cod);
    }
    
    private boolean isJugadorFormFilled(){
        return (
                this.jTenistaDniTextField.isFocusOwner()
                );
    }
    
        /**
     * Obtener Partocinador seleccionado de la tabla 
     */

    private Pedido getPedidoSeleccionado() throws DAOException{
        String cod = (String) jPedidosTable.getValueAt(jPedidosTable.getSelectedRow(), 0);
        return manager.getPedidoDAO().obtener(cod);
    }

    private Arbitro getArbitroSeleccionado() throws DAOException {
        String cod = (String) jArbitrosOfertablesTable.getValueAt(jArbitrosOfertablesTable.getSelectedRow(), 0);
        return manager.getArbitroDAO().obtener(cod);
    }
    
    private Arbitro getArbitroPartidoSeleccionado() throws DAOException {
        String cod = (String) jArbitrosTable.getValueAt(jArbitrosTable.getSelectedRow(), 0);
        return manager.getArbitroDAO().obtener(cod);
    }
    
    private Tenista getTenistaSeleccionado() throws DAOException{
        String cod = (String) jTenistaTable.getValueAt(jTenistaTable.getSelectedRow(), 0);
        Integer edicion = Integer.parseInt(jTenistaTable.getValueAt(jTenistaTable.getSelectedRow(), 3).toString());
        return manager.getTenistaDAO().obtener(cod,edicion);
    }
    
    private Partido getPartidoSeleccionado() throws DAOException {
        String cod = (String) jPartidosTable.getValueAt(jPartidosTable.getSelectedRow(), 0);
        return manager.getPartidoDAO().obtener(cod);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        patrocinadoresButton = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        patrocinadorestext = new javax.swing.JLabel();
        pedidosButton = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        arbitrosButton = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        ofertasButton = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        rankingButton = new javax.swing.JPanel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cardLayoutPanel = new javax.swing.JPanel();
        PatrocinadoresPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPatrocinadoresTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        registerPatrocinadorButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        pedidosPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPedidosTable = new javax.swing.JTable();
        cancelOrderButton = new javax.swing.JButton();
        newOrderButton = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jMaterialesPedidoTable = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        arbitrosPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jArbitrosTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPartidosTable = new javax.swing.JTable();
        jNuevoPartidoButton = new javax.swing.JButton();
        ofertasPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jArbitrosOfertablesTable = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jOfertasRegistradasTable = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        newOfferButton1 = new javax.swing.JButton();
        removeOfferButton = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        rankingPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTenistaTable = new javax.swing.JTable();
        jEdicionBox = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        NewTenistaPanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTenistaDniTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTenistaNombreTextField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTenistaPosicionTextfField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTenistaEdicionSelectionBox = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jTenistaApellidosTextField = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        nuevoTenistaButton = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jEliminarTenistaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N

        jPanel1.setPreferredSize(new java.awt.Dimension(670, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(180, 470));
        jPanel2.setMinimumSize(new java.awt.Dimension(180, 470));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/logo.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/menu_open.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        patrocinadoresButton.setBackground(new java.awt.Color(255, 255, 255));
        patrocinadoresButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        patrocinadoresButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        patrocinadoresButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patrocinadoresButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                patrocinadoresButtonMouseEntered(evt);
            }
        });
        patrocinadoresButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                patrocinadoresButtonKeyPressed(evt);
            }
        });
        patrocinadoresButton.setLayout(new javax.swing.BoxLayout(patrocinadoresButton, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator8.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator8.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jSeparator8.setEnabled(false);
        jSeparator8.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator8.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator8.setName(""); // NOI18N
        jSeparator8.setPreferredSize(new java.awt.Dimension(10, 10));
        patrocinadoresButton.add(jSeparator8);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/patrocinadores.png"))); // NOI18N
        patrocinadoresButton.add(jLabel4);

        jSeparator7.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator7.setEnabled(false);
        jSeparator7.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator7.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator7.setName(""); // NOI18N
        jSeparator7.setPreferredSize(new java.awt.Dimension(10, 10));
        patrocinadoresButton.add(jSeparator7);

        patrocinadorestext.setBackground(new java.awt.Color(0, 0, 0));
        patrocinadorestext.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        patrocinadorestext.setForeground(new java.awt.Color(0, 0, 0));
        patrocinadorestext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        patrocinadorestext.setText("Patrocinadores");
        patrocinadorestext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patrocinadorestextMouseClicked(evt);
            }
        });
        patrocinadoresButton.add(patrocinadorestext);

        jPanel2.add(patrocinadoresButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 180, 40));

        pedidosButton.setBackground(new java.awt.Color(255, 255, 255));
        pedidosButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pedidosButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        pedidosButton.setPreferredSize(new java.awt.Dimension(96, 17));
        pedidosButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pedidosButtonMouseClicked(evt);
            }
        });
        pedidosButton.setLayout(new javax.swing.BoxLayout(pedidosButton, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator6.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setEnabled(false);
        jSeparator6.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator6.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator6.setName(""); // NOI18N
        jSeparator6.setPreferredSize(new java.awt.Dimension(10, 10));
        pedidosButton.add(jSeparator6);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/pedidos.png"))); // NOI18N
        jLabel5.setAlignmentX(0.5F);
        pedidosButton.add(jLabel5);

        jSeparator5.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator5.setEnabled(false);
        jSeparator5.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator5.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator5.setName(""); // NOI18N
        jSeparator5.setPreferredSize(new java.awt.Dimension(10, 10));
        pedidosButton.add(jSeparator5);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Pedidos");
        pedidosButton.add(jLabel6);

        jPanel2.add(pedidosButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 180, 40));

        arbitrosButton.setBackground(new java.awt.Color(255, 255, 255));
        arbitrosButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        arbitrosButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        arbitrosButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbitrosButtonMouseClicked(evt);
            }
        });
        arbitrosButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                arbitrosButtonKeyPressed(evt);
            }
        });
        arbitrosButton.setLayout(new javax.swing.BoxLayout(arbitrosButton, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator4.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator4.setEnabled(false);
        jSeparator4.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator4.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator4.setName(""); // NOI18N
        jSeparator4.setPreferredSize(new java.awt.Dimension(10, 10));
        arbitrosButton.add(jSeparator4);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/arbitros.png"))); // NOI18N
        arbitrosButton.add(jLabel7);

        jSeparator3.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator3.setEnabled(false);
        jSeparator3.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator3.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator3.setName(""); // NOI18N
        jSeparator3.setPreferredSize(new java.awt.Dimension(10, 10));
        arbitrosButton.add(jSeparator3);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Árbitros y Partidos");
        arbitrosButton.add(jLabel8);

        jPanel2.add(arbitrosButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 180, 40));

        ofertasButton.setBackground(new java.awt.Color(255, 255, 255));
        ofertasButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        ofertasButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        ofertasButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ofertasButtonMouseClicked(evt);
            }
        });
        ofertasButton.setLayout(new javax.swing.BoxLayout(ofertasButton, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setEnabled(false);
        jSeparator1.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator1.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator1.setName(""); // NOI18N
        jSeparator1.setPreferredSize(new java.awt.Dimension(10, 10));
        ofertasButton.add(jSeparator1);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/ofertas.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        ofertasButton.add(jLabel9);

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setEnabled(false);
        jSeparator2.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator2.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator2.setName(""); // NOI18N
        jSeparator2.setPreferredSize(new java.awt.Dimension(10, 10));
        ofertasButton.add(jSeparator2);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Negociar Ofertas");
        ofertasButton.add(jLabel10);

        jPanel2.add(ofertasButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 180, 40));

        rankingButton.setBackground(new java.awt.Color(255, 255, 255));
        rankingButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        rankingButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        rankingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rankingButtonMouseClicked(evt);
            }
        });
        rankingButton.setLayout(new javax.swing.BoxLayout(rankingButton, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator10.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator10.setEnabled(false);
        jSeparator10.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator10.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator10.setName(""); // NOI18N
        jSeparator10.setPreferredSize(new java.awt.Dimension(10, 10));
        rankingButton.add(jSeparator10);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/ranking.png"))); // NOI18N
        rankingButton.add(jLabel12);

        jSeparator9.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator9.setEnabled(false);
        jSeparator9.setMaximumSize(new java.awt.Dimension(10, 10));
        jSeparator9.setMinimumSize(new java.awt.Dimension(10, 10));
        jSeparator9.setName(""); // NOI18N
        jSeparator9.setPreferredSize(new java.awt.Dimension(10, 10));
        rankingButton.add(jSeparator9);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Ranking");
        rankingButton.add(jLabel11);

        jPanel2.add(rankingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 180, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 470));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/menu_closed.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        cardLayoutPanel.setLayout(new java.awt.CardLayout());

        PatrocinadoresPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(645, 800));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        jPatrocinadoresTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPatrocinadoresTable.setForeground(new java.awt.Color(0, 0, 0));
        jPatrocinadoresTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jPatrocinadoresTable.setGridColor(new java.awt.Color(0, 0, 0));
        jPatrocinadoresTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
        jPatrocinadoresTable.setRowHeight(40);
        jPatrocinadoresTable.getTableHeader().setResizingAllowed(false);
        jPatrocinadoresTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jPatrocinadoresTable);
        jPatrocinadoresTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        registerPatrocinadorButton.setBackground(new java.awt.Color(156, 255, 160));
        registerPatrocinadorButton.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        registerPatrocinadorButton.setForeground(new java.awt.Color(0, 0, 0));
        registerPatrocinadorButton.setText("Registrar");
        registerPatrocinadorButton.setFocusable(false);
        registerPatrocinadorButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        registerPatrocinadorButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        registerPatrocinadorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerPatrocinadorButtonMouseClicked(evt);
            }
        });
        registerPatrocinadorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerPatrocinadorButtonActionPerformed(evt);
            }
        });

        eliminarButton.setBackground(new java.awt.Color(255, 142, 142));
        eliminarButton.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        eliminarButton.setForeground(new java.awt.Color(0, 0, 0));
        eliminarButton.setText("Eliminar");
        eliminarButton.setFocusable(false);
        eliminarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        eliminarButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Patrocinadores Registrados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(registerPatrocinadorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)
                        .addComponent(eliminarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerPatrocinadorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(345, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PatrocinadoresPanelLayout = new javax.swing.GroupLayout(PatrocinadoresPanel);
        PatrocinadoresPanel.setLayout(PatrocinadoresPanelLayout);
        PatrocinadoresPanelLayout.setHorizontalGroup(
            PatrocinadoresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1514, Short.MAX_VALUE)
        );
        PatrocinadoresPanelLayout.setVerticalGroup(
            PatrocinadoresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PatrocinadoresPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cardLayoutPanel.add(PatrocinadoresPanel, "card2");

        pedidosPanel.setBackground(new java.awt.Color(255, 255, 255));
        pedidosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setBackground(new java.awt.Color(0, 0, 0));
        jLabel35.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Pedidos Realizados");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(521, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addContainerGap())
        );

        pedidosPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 40));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPedidosTable.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPedidosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jPedidosTable.setRowHeight(40);
        jPedidosTable.getTableHeader().setResizingAllowed(false);
        jPedidosTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jPedidosTable);

        pedidosPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 510, 380));

        cancelOrderButton.setBackground(new java.awt.Color(255, 137, 137));
        cancelOrderButton.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cancelOrderButton.setForeground(new java.awt.Color(0, 0, 0));
        cancelOrderButton.setText("Eliminar Pedido");
        cancelOrderButton.setFocusable(false);
        cancelOrderButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelOrderButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOrderButtonActionPerformed(evt);
            }
        });
        cancelOrderButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cancelOrderButtonKeyPressed(evt);
            }
        });
        pedidosPanel.add(cancelOrderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 170, 29));

        newOrderButton.setBackground(new java.awt.Color(169, 255, 153));
        newOrderButton.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        newOrderButton.setForeground(new java.awt.Color(0, 0, 0));
        newOrderButton.setText("Nuevo Pedido");
        newOrderButton.setFocusable(false);
        newOrderButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newOrderButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newOrderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newOrderButtonMouseClicked(evt);
            }
        });
        newOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderButtonActionPerformed(evt);
            }
        });
        pedidosPanel.add(newOrderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 170, 29));

        jMaterialesPedidoTable.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMaterialesPedidoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jMaterialesPedidoTable.setRowHeight(30);
        jScrollPane8.setViewportView(jMaterialesPedidoTable);

        pedidosPanel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, 370, 340));

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Materiales Disponibles");
        pedidosPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 262, -1));

        cardLayoutPanel.add(pedidosPanel, "card3");

        arbitrosPanel.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        jArbitrosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jArbitrosTable.setRowHeight(40);
        jArbitrosTable.getTableHeader().setResizingAllowed(false);
        jArbitrosTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jArbitrosTable);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Actualizar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel29.setBackground(new java.awt.Color(0, 0, 0));
        jLabel29.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Árbitros ");

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/arbitro.png"))); // NOI18N

        jLabel31.setBackground(new java.awt.Color(0, 0, 0));
        jLabel31.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Partidos");

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/partidos.png"))); // NOI18N

        jPartidosTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPartidosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jPartidosTable.setRowHeight(40);
        jScrollPane7.setViewportView(jPartidosTable);

        jNuevoPartidoButton.setBackground(new java.awt.Color(125, 167, 255));
        jNuevoPartidoButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jNuevoPartidoButton.setForeground(new java.awt.Color(0, 0, 0));
        jNuevoPartidoButton.setText("Nuevo Partido");
        jNuevoPartidoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jNuevoPartidoButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout arbitrosPanelLayout = new javax.swing.GroupLayout(arbitrosPanel);
        arbitrosPanel.setLayout(arbitrosPanelLayout);
        arbitrosPanelLayout.setHorizontalGroup(
            arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(arbitrosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(arbitrosPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                .addGroup(arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(arbitrosPanelLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addGap(169, 169, 169))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(arbitrosPanelLayout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, arbitrosPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jNuevoPartidoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(360, 360, 360))
        );
        arbitrosPanelLayout.setVerticalGroup(
            arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(arbitrosPanelLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel31)
                        .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel30)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(arbitrosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jNuevoPartidoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        cardLayoutPanel.add(arbitrosPanel, "card4");

        ofertasPanel.setBackground(new java.awt.Color(255, 255, 255));
        ofertasPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        //jScrollPane4.setViewportView(jArbitrosTable);

        jArbitrosOfertablesTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jArbitrosOfertablesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jArbitrosOfertablesTable.setRowHeight(40);
        jArbitrosOfertablesTable.getTableHeader().setResizingAllowed(false);
        jArbitrosOfertablesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jArbitrosOfertablesTable);

        ofertasPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 460, 300));

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Ofertas Registradas");
        ofertasPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        jOfertasRegistradasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jOfertasRegistradasTable.setRowHeight(40);
        jOfertasRegistradasTable.getTableHeader().setResizingAllowed(false);
        jOfertasRegistradasTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jOfertasRegistradasTable);

        ofertasPanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 460, 300));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/newOfferIcon.png"))); // NOI18N
        ofertasPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, -1, -1));

        newOfferButton1.setBackground(new java.awt.Color(153, 255, 153));
        newOfferButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        newOfferButton1.setForeground(new java.awt.Color(0, 0, 0));
        newOfferButton1.setText("Realizar Oferta");
        newOfferButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newOfferButton1MouseClicked(evt);
            }
        });
        ofertasPanel.add(newOfferButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 190, 40));

        removeOfferButton.setBackground(new java.awt.Color(255, 102, 102));
        removeOfferButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        removeOfferButton.setForeground(new java.awt.Color(0, 0, 0));
        removeOfferButton.setText("Eliminar Oferta");
        removeOfferButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeOfferButtonMouseClicked(evt);
            }
        });
        ofertasPanel.add(removeOfferButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, 190, 40));

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/arbitro.png"))); // NOI18N
        ofertasPanel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, 30));

        jLabel34.setBackground(new java.awt.Color(0, 0, 0));
        jLabel34.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Árbitros ");
        ofertasPanel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, 20));

        cardLayoutPanel.add(ofertasPanel, "card5");

        rankingPanel.setBackground(new java.awt.Color(255, 255, 255));
        rankingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTenistaTable.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTenistaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTenistaTable.setRowHeight(40);
        jTenistaTable.getTableHeader().setResizingAllowed(false);
        jTenistaTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(jTenistaTable);

        rankingPanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 710, 340));

        jEdicionBox.setBackground(new java.awt.Color(255, 255, 255));
        jEdicionBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jEdicionBox.setForeground(new java.awt.Color(0, 0, 0));
        jEdicionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1ª Ed.", "2ª Ed.", "3ª Ed."}));
        rankingPanel.add(jEdicionBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 120, 40));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/rankingTableIcon.png"))); // NOI18N
        rankingPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 60, 60));

        NewTenistaPanel.setBackground(new java.awt.Color(255, 255, 255));
        NewTenistaPanel.setForeground(new java.awt.Color(255, 255, 255));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/newPlayer.png"))); // NOI18N
        jLabel23.setMaximumSize(new java.awt.Dimension(32, 50));
        jLabel23.setMinimumSize(new java.awt.Dimension(32, 50));
        jLabel23.setPreferredSize(new java.awt.Dimension(32, 50));

        jLabel20.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("DNI");

        jTenistaDniTextField.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Nombre");

        jTenistaNombreTextField.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Apellidos");

        jTenistaPosicionTextfField.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Posición");

        jTenistaEdicionSelectionBox.setBackground(new java.awt.Color(255, 255, 255));
        jTenistaEdicionSelectionBox.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTenistaEdicionSelectionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));

        jLabel26.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Nº de Edición");

        jTenistaApellidosTextField.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("ª");

        nuevoTenistaButton.setBackground(new java.awt.Color(193, 255, 184));
        nuevoTenistaButton.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nuevoTenistaButton.setForeground(new java.awt.Color(0, 0, 0));
        nuevoTenistaButton.setText("Registrar ");
        nuevoTenistaButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevoTenistaButtonMouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("ª");

        javax.swing.GroupLayout NewTenistaPanelLayout = new javax.swing.GroupLayout(NewTenistaPanel);
        NewTenistaPanel.setLayout(NewTenistaPanelLayout);
        NewTenistaPanelLayout.setHorizontalGroup(
            NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                .addGroup(NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                        .addGroup(NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                                        .addComponent(jTenistaPosicionTextfField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel27))
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jTenistaApellidosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24)
                                    .addComponent(jTenistaNombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)
                                    .addComponent(jTenistaDniTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                                        .addComponent(jTenistaEdicionSelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel28)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(nuevoTenistaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        NewTenistaPanelLayout.setVerticalGroup(
            NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewTenistaPanelLayout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTenistaDniTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTenistaNombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTenistaApellidosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addGap(12, 12, 12)
                .addGroup(NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTenistaEdicionSelectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(12, 12, 12)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NewTenistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTenistaPosicionTextfField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(nuevoTenistaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rankingPanel.add(NewTenistaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 200, 440));

        jEliminarTenistaButton.setBackground(new java.awt.Color(255, 131, 131));
        jEliminarTenistaButton.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jEliminarTenistaButton.setForeground(new java.awt.Color(0, 0, 0));
        jEliminarTenistaButton.setText("Eliminar");
        jEliminarTenistaButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jEliminarTenistaButtonMouseClicked(evt);
            }
        });
        rankingPanel.add(jEliminarTenistaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(823, 55, 120, 30));

        cardLayoutPanel.add(rankingPanel, "card6");

        jPanel1.add(cardLayoutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 960, 659));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1160, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
    if( x == 0 ){
            jPanel2.setSize(x, 470);
            Thread th = new Thread(){
                @Override
                public void run(){
                    try {
                        for (int i = 0; i <= x; i+=3){
                            Thread.sleep(1);
                            jPanel2.setSize(i, 470);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            };th.start();
            x = 210;
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void patrocinadorestextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patrocinadorestextMouseClicked
        CardLayout cl = (CardLayout)(cardLayoutPanel.getLayout());
        cl.show(cardLayoutPanel,"card2");
        patrocinadoresButton.setBackground(selectedButtonColor);
        arbitrosButton.setBackground(Color.WHITE);
        pedidosButton.setBackground(Color.white);
        ofertasButton.setBackground(Color.WHITE);
        rankingButton.setBackground(Color.white);

        System.out.print("mostrando lista patrocinadores");
    }//GEN-LAST:event_patrocinadorestextMouseClicked

    private void pedidosButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pedidosButtonMouseClicked
        CardLayout cl = (CardLayout)(cardLayoutPanel.getLayout());
        pedidosButton.setBackground(selectedButtonColor);
        patrocinadoresButton.setBackground(Color.WHITE);
        arbitrosButton.setBackground(Color.white);
        ofertasButton.setBackground(Color.white);
        rankingButton.setBackground(Color.white);

        try {
            manager.getPedidoDAO().obtenerTodos();
        } catch (DAOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pedidosModel.updateModel();
        } catch (DAOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // notificar de los cambios al listener
        pedidosModel.fireTableDataChanged();
        
        cl.show(cardLayoutPanel,"card3");
        System.out.print("mostrando lista de pedidos");
    }//GEN-LAST:event_pedidosButtonMouseClicked

    private void arbitrosButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbitrosButtonMouseClicked
        

        try {
            partidosModel.updateModel();      arbitrosModel.updateModel();
            partidosModel.fireTableDataChanged();  arbitrosModel.fireTableDataChanged();
            
            CardLayout cl = (CardLayout)(cardLayoutPanel.getLayout());
            cl.show(cardLayoutPanel,"card4");
            pedidosButton.setBackground(Color.WHITE);
            arbitrosButton.setBackground(selectedButtonColor);
            patrocinadoresButton.setBackground(Color.WHITE);
            ofertasButton.setBackground(Color.white);
            rankingButton.setBackground(Color.white);
            
            
        } catch (DAOException ex) {
            JOptionPane.showConfirmDialog(null, ex.getLocalizedMessage()+": no se han podido actualizar los arbitros o los partidos: \n"+ex.getCause(), 
                    "error al consultar",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_arbitrosButtonMouseClicked

    private void patrocinadoresButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_patrocinadoresButtonKeyPressed
    }//GEN-LAST:event_patrocinadoresButtonKeyPressed

    private void arbitrosButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_arbitrosButtonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_arbitrosButtonKeyPressed

    private void ofertasButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ofertasButtonMouseClicked
        CardLayout cl = (CardLayout)(cardLayoutPanel.getLayout());
        cl.show(cardLayoutPanel,"card5");
        
        ofertasButton.setBackground(selectedButtonColor);
        pedidosButton.setBackground(Color.white);
        patrocinadoresButton.setBackground(Color.WHITE);
        arbitrosButton.setBackground(Color.white);
        rankingButton.setBackground(Color.white);
        // actualizar las tablas 
        try {
            ofertasModel.updateModel();
            ofertasModel.fireTableDataChanged();
            arbitrosModel.updateModel();
            arbitrosModel.fireTableDataChanged();
        } catch (DAOException ex) {
            JOptionPane.showConfirmDialog(null, ex.getLocalizedMessage()+": no se han podido actualizar las ofertas o los arbitros: \n"+ex.getCause(), 
                    "error al consultar",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
        }
        
        
        System.out.print("mostrando card ofertas");
    }//GEN-LAST:event_ofertasButtonMouseClicked

    private void rankingButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rankingButtonMouseClicked
        CardLayout cl = (CardLayout)(cardLayoutPanel.getLayout());
        cl.show(cardLayoutPanel,"card6");
        
        rankingButton.setBackground(selectedButtonColor);       
        pedidosButton.setBackground(Color.white);
        patrocinadoresButton.setBackground(Color.WHITE);
        arbitrosButton.setBackground(Color.white);
        ofertasButton.setBackground(Color.white);

    }//GEN-LAST:event_rankingButtonMouseClicked

    
    
    /** 
     * Al pulsar boton , actualizar tableModel llamando a la BD 
     * Mostrar mensaje de error al haber DAOException
    */
    private void patrocinadoresButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patrocinadoresButtonMouseClicked
        patrocinadoresButton.setBackground(selectedButtonColor);
        arbitrosButton.setBackground(Color.WHITE);
        pedidosButton.setBackground(Color.WHITE);
        ofertasButton.setBackground(Color.WHITE);
        rankingButton.setBackground(Color.WHITE);
        
        try {
            this.patrocinadoresModel.updateModel();
            this.jPatrocinadoresTable.setModel(patrocinadoresModel);
            CardLayout cl = (CardLayout)(cardLayoutPanel.getLayout());
            cl.show(cardLayoutPanel,"card2");
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(null, "error");
        }

    }//GEN-LAST:event_patrocinadoresButtonMouseClicked

    private void patrocinadoresButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patrocinadoresButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_patrocinadoresButtonMouseEntered

    private void registerPatrocinadorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerPatrocinadorButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerPatrocinadorButtonActionPerformed

    // Abrir joption pane para registrar patrocinador
    private void registerPatrocinadorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerPatrocinadorButtonMouseClicked

        // Ventanas emergentes para la inserción de datos 
        RegistrarPatrocinadorPanel myPanel = new RegistrarPatrocinadorPanel();
        
        
        // Pop up para insertar un patrocinador
        int result = JOptionPane.showConfirmDialog(null, myPanel,
            "Registro de Patrocinador", JOptionPane.OK_CANCEL_OPTION ,
            JOptionPane.PLAIN_MESSAGE);
        
        // Si se da al ok , lammar a la BD para insertar el Patrocinador
        // Actualizar Table Model de Patrocinadores tras esto
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                manager.getPatrocinadorDAO().insertar(myPanel.getPatrocinador());
                patrocinadoresModel.updateModel();
                //Notificar a los listener que el valor de la tabla ha cambiado
                patrocinadoresModel.fireTableDataChanged(); 

            } catch (DAOException ex) {
                JOptionPane.showConfirmDialog(null, ex.getLocalizedMessage()+": no se ha podido insertar el patrocinador\n"+ex.getCause(), "error al insertar",JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_registerPatrocinadorButtonMouseClicked

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        if (JOptionPane.showConfirmDialog(rootPane, "¿Eliminar al Patrocinador?",
               "Borrar Stock" , JOptionPane.YES_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            try {
                // obtener stock seleccionado
                Patrocinador s = getPatrocinadorSeleccionado();
                // llamar al manager para eliminarlo de la bd
                manager.getPatrocinadorDAO().eliminar(s.getCif());
                patrocinadoresModel.updateModel();
                // notificar de los cambios al listener
                patrocinadoresModel.fireTableDataChanged();
                
            } catch (DAOException ex) {
                
            }
        }
    }//GEN-LAST:event_eliminarButtonActionPerformed

    private void newOrderButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newOrderButtonMouseClicked
        // Ventanas emergentes para la inserción de datos 
        NuevoPedidoPanel myPanel = null;
        try {
            myPanel = new NuevoPedidoPanel(manager);
        } catch (DAOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Pop up para insertar un patrocinador
        int result = JOptionPane.showConfirmDialog(null, myPanel,
            "Hacer un Pedido", JOptionPane.OK_CANCEL_OPTION ,
            JOptionPane.PLAIN_MESSAGE);
        
        // Si se da al ok , lammar a la BD para insertar el Pedido
        // Actualizar Table Model de Pedidos tras esto
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                manager.getPedidoDAO().insertar(myPanel.getPedido());
                pedidosModel.updateModel();
                // Notificar a los listener de que el valor de la tabla ha cambiado
                pedidosModel.fireTableDataChanged(); 

            // en caso de error mostrar vetana con el código de error SQL O dao
            } catch (DAOException ex) {
                JOptionPane.showConfirmDialog(null, ex.getLocalizedMessage()+": no se ha podido hacer el pedido\n"+ex.getCause(), "error al insertar",JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
            
        }        
    }//GEN-LAST:event_newOrderButtonMouseClicked

    private void newOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOrderButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newOrderButtonActionPerformed

    private void cancelOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOrderButtonActionPerformed
        if (JOptionPane.showConfirmDialog(rootPane, "¿Eliminar Producto del Registro?",
               "Borrar Stock" , JOptionPane.YES_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            try {
                // obtener stock seleccionado
                Pedido s = getPedidoSeleccionado();
                // llamar al manager para eliminarlo de la bd
                manager.getPedidoDAO().eliminar(s.getCod(),s.getCodMaterial());
                pedidosModel.updateModel();
                // notificar de los cambios al listener
                pedidosModel.fireTableDataChanged();
                
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "no se ha podido eliminar el pedido "+ex.getCause());
            }
        }
    }//GEN-LAST:event_cancelOrderButtonActionPerformed

    private void cancelOrderButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cancelOrderButtonKeyPressed
        
    }//GEN-LAST:event_cancelOrderButtonKeyPressed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        updateArbitrosTable();
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * 
     * Devolver cadena de 
     * 
     */
    String getRandomCode(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
    
    private void removeOfferButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeOfferButtonMouseClicked
    if (JOptionPane.showConfirmDialog(rootPane, "¿Eliminar oferta?",
               "Borrar oferta" , JOptionPane.YES_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            try {
                // obtener stock seleccionado
                Oferta s = getOfertaSeleccionada();
                // llamar al manager para eliminarlo de la bd
                manager.getOfertaDAO().eliminar(s.getCod_oferta());
                ofertasModel.updateModel();
                // notificar de los cambios al listener
                ofertasModel.fireTableDataChanged();
                
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "No se ha podido eliminar la oferta :"+ex.getMessage());
            }
        }   
    }//GEN-LAST:event_removeOfferButtonMouseClicked

    private void newOfferButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newOfferButton1MouseClicked
        
        Arbitro selected;
        NuevaOfertaPanel myPanel = null;
        try {
            selected = getArbitroSeleccionado();
             myPanel = new NuevaOfertaPanel(getRandomCode(5), selected.getDni(), obtenerFechaActual());

        } catch (DAOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Ventanas emergentes para la inserción de datos 
        
        
        // Pop up para insertar una oferta
        int result = JOptionPane.showConfirmDialog(null, myPanel,
            "Registro una nueva Oferta", JOptionPane.OK_CANCEL_OPTION ,
            JOptionPane.PLAIN_MESSAGE);
        
        // Si se da al ok , lammar a la BD para insertar el Patrocinador
        // Actualizar Table Model de Patrocinadores tras esto
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                manager.getOfertaDAO().insertar(myPanel.getOferta());
                ofertasModel.updateModel();
                //Notificar a los listener que el valor de la tabla ha cambiado
                ofertasModel.fireTableDataChanged(); 

            } catch (DAOException ex) {
                JOptionPane.showConfirmDialog(null, ex.getLocalizedMessage()+": no se ha podido insertar el patrocinador\n"+ex.getCause(), "error al insertar",JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_newOfferButton1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if ( x == 210 ) {
            jPanel2.setSize(210, 552);
            Thread th = new Thread() {
                @Override
                public void run(){
                    try {

                        for ( int i = 210; i >= 0; i-=3){
                            Thread.sleep(1);
                            jPanel2.setSize(i, 470);

                            a++;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            };
            th.start();
            x=0;
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    /**
     * Obtener los datos insertados en la UI del tenista
     */
    private Tenista obtenerDatosTenista(){
        
        Integer posicion = Integer.parseInt(jTenistaPosicionTextfField.getText());
        Integer numEdicion;
        switch (jTenistaEdicionSelectionBox.getSelectedItem().toString()) {
            case "1":
                numEdicion = 1;
                break;
            case "2":
                numEdicion = 2;
                break;
            case "3":
                numEdicion = 3;
                break;
            default:
                numEdicion = 1;
        }
        
        return new Tenista(
                jTenistaDniTextField.getText(),
                jTenistaNombreTextField.getText(),
                jTenistaApellidosTextField.getText(),
                numEdicion,
                posicion
        );
    }
    
    private void nuevoTenistaButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nuevoTenistaButtonMouseClicked
        System.out.println(isJugadorFormFilled());
        Tenista nuevo = obtenerDatosTenista();
        try {
            manager.getTenistaDAO().insertar(nuevo);
            tenistasModel.updateModel(nuevo.getPosicionRanking());
            
        } catch (DAOException ex) {
           JOptionPane.showConfirmDialog(null, "Error al insertar tenista :\n"+ex.getMessage(),
            "Crosscourt", JOptionPane.DEFAULT_OPTION ,
            JOptionPane.ERROR_MESSAGE);
        }
        // Notificar a los listener de que el valor de la tabla ha cambiado
        tenistasModel.fireTableDataChanged();
        
    }//GEN-LAST:event_nuevoTenistaButtonMouseClicked

    private void jEliminarTenistaButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEliminarTenistaButtonMouseClicked
        if (JOptionPane.showConfirmDialog(rootPane, "¿Eliminar al Jugador?",
               "Borrar Jugador" , JOptionPane.YES_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            try {
                // obtener stock seleccionado
                Tenista s = getTenistaSeleccionado();
                // llamar al manager para eliminarlo de la bd
                manager.getTenistaDAO().eliminar(s.getDni(),s.getNumEdicion());
                tenistasModel.updateModel(s.getNumEdicion());
                // notificar de los cambios al listener
                tenistasModel.fireTableDataChanged();
                
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "No se ha podido eliminar al Tenista :"+ex.getMessage());
            }
        }   
    }//GEN-LAST:event_jEliminarTenistaButtonMouseClicked

    private void jNuevoPartidoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jNuevoPartidoButtonMouseClicked
        Arbitro selected;
        NuevoPartidoPanel myPanel = null;
        try {
            selected = getArbitroPartidoSeleccionado();
            // generar un codigo random para el codigo del partido de 4 caracteres (en las especificaciones
            // establecimos este tamaño de código)
            myPanel = new NuevoPartidoPanel(getRandomCode(4), selected.getDni());

        } catch (DAOException ex) {
            JOptionPane.showConfirmDialog(null, "Debes seleccionar un Árbitro para el partido",
            "Crosscourt", JOptionPane.DEFAULT_OPTION ,
            JOptionPane.ERROR_MESSAGE);
        }
        
        // Ventanas emergentes para la inserción de datos 
        
        
        // Pop up para insertar una oferta
        int result = JOptionPane.showConfirmDialog(null, myPanel,
            "Registro una nueva Oferta", JOptionPane.OK_CANCEL_OPTION ,
            JOptionPane.PLAIN_MESSAGE);
        
        // Si se da al ok , lammar a la BD para insertar el Patrocinador
        // Actualizar Table Model de Patrocinadores tras esto
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                manager.getPartidoDAO().insertar(myPanel.getPartido());
                partidosModel.updateModel();
                //Notificar a los listener que el valor de la tabla ha cambiado
                partidosModel.fireTableDataChanged(); 

            } catch (DAOException ex) {
                JOptionPane.showConfirmDialog(null, ex.getLocalizedMessage()+": no se ha podido insertar el partido\n"+ex.getCause(), "error al insertar",JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_jNuevoPartidoButtonMouseClicked

    private void updateArbitrosTable(){
        try {
            arbitrosModel.updateModel();
            arbitrosModel.fireTableDataChanged();
        } catch (DAOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
        Al pulsar el jlabel2 (menu icon) abrir / cerrar menu contextual
    */
    int x = 210;
    int a = 0;
    
    /**
     ************* MAIN ****************
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NewTenistaPanel;
    private javax.swing.JPanel PatrocinadoresPanel;
    private javax.swing.JPanel arbitrosButton;
    private javax.swing.JPanel arbitrosPanel;
    private javax.swing.JButton cancelOrderButton;
    private javax.swing.JPanel cardLayoutPanel;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JTable jArbitrosOfertablesTable;
    private javax.swing.JTable jArbitrosTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jEdicionBox;
    private javax.swing.JButton jEliminarTenistaButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTable jMaterialesPedidoTable;
    private javax.swing.JButton jNuevoPartidoButton;
    private javax.swing.JTable jOfertasRegistradasTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTable jPartidosTable;
    private javax.swing.JTable jPatrocinadoresTable;
    private javax.swing.JTable jPedidosTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTenistaApellidosTextField;
    private javax.swing.JTextField jTenistaDniTextField;
    private javax.swing.JComboBox<String> jTenistaEdicionSelectionBox;
    private javax.swing.JTextField jTenistaNombreTextField;
    private javax.swing.JTextField jTenistaPosicionTextfField;
    private javax.swing.JTable jTenistaTable;
    private javax.swing.JButton newOfferButton1;
    private javax.swing.JButton newOrderButton;
    private javax.swing.JButton nuevoTenistaButton;
    private javax.swing.JPanel ofertasButton;
    private javax.swing.JPanel ofertasPanel;
    private javax.swing.JPanel patrocinadoresButton;
    private javax.swing.JLabel patrocinadorestext;
    private javax.swing.JPanel pedidosButton;
    private javax.swing.JPanel pedidosPanel;
    private javax.swing.JPanel rankingButton;
    private javax.swing.JPanel rankingPanel;
    private javax.swing.JButton registerPatrocinadorButton;
    private javax.swing.JButton removeOfferButton;
    // End of variables declaration//GEN-END:variables

    private Oferta getOfertaSeleccionada() throws DAOException {
        String cod = (String) jOfertasRegistradasTable.getValueAt(jOfertasRegistradasTable.getSelectedRow(), 0);
        return manager.getOfertaDAO().obtener(cod);
    }
    
    /**
     * Obtener String fecha actual en formato 
     * dd / MM / yyyy
     * (que es el establecido para la BD , esta espera un tipo DATE)
    */
     String obtenerFechaActual(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);  
     }

    public static void main(String args[])  {
        
        
        // CREACIÓN DEL DAO MANAGER
        
        try{
            OracleDAOManager manager ;
            manager = new OracleDAOManager("x6592266","x6592266");
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>




            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        try {
                            Image i = ImageIO.read(getClass().getResource("/main/resources/ICON_LOGO.png"));
                            MainMenu mainMenu = new MainMenu(manager);
                            mainMenu.setVisible(true);
                            mainMenu.setResizable(false);
                            mainMenu.setIconImage(i);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (DAOException ex) {
                        Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
        }catch(SQLException e){
            JOptionPane.showConfirmDialog(null, "Error: Debes conectarte a la VPN de la UGR",
            "Crosscourt", JOptionPane.DEFAULT_OPTION ,
            JOptionPane.ERROR_MESSAGE);
        }
    }
     
     
    
}
