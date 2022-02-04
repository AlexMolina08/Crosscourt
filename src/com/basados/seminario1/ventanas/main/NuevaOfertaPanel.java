/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.ventanas.main;

import com.basados.seminario1.model.Oferta;
import com.basados.seminario1.model.Pedido;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Alex
 */
public class NuevaOfertaPanel extends javax.swing.JPanel {

    private Oferta oferta ;
    
    /**
     * Devolver la oferta resultante basada en el estado actual de la pestaña
     * @return 
     */
    public Oferta getOferta() {
        oferta.setNum_edicion(Integer.parseInt(jEdicionTextField.getText()));
        oferta.setCantidad(Double.parseDouble(jCantidadTextField.getText()));
        System.out.println(jEdicionTextField.getText());
        return oferta;
        
    }
    
    /**
     * Constructor de oferta panel
     */
    public NuevaOfertaPanel(String cod_oferta, String dniArbitro , String fecha) {
        
        oferta = new Oferta(cod_oferta, dniArbitro, 0 , fecha , 0 , 0);
        
        jCodOfertaLabel = new JLabel(cod_oferta);
        JDniArbitroLabel = new JLabel(dniArbitro);
        jFechaLabel = new JLabel(fecha);
        
        
        super.update(this.getGraphics());
        initComponents();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        JDniArbitroLabel = new javax.swing.JLabel();
        jFechaLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jCodOfertaLabel = new javax.swing.JLabel();
        jEdicionTextField = new javax.swing.JTextField();
        jCantidadTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(302, 354));
        setPreferredSize(new java.awt.Dimension(302, 354));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(355, 389));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nueva Oferta");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 88, -1, -1));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("DNI Árbitro");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 172, -1, 19));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Fecha");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 234, -1, -1));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nº de Edición");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 204, -1, -1));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Valor de la oferta");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/resources/offerIconMenu.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 12, -1, -1));

        JDniArbitroLabel.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        JDniArbitroLabel.setForeground(new java.awt.Color(0, 0, 0));
        JDniArbitroLabel.setText(oferta.getDniArbitro());
        jPanel1.add(JDniArbitroLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

        jFechaLabel.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jFechaLabel.setForeground(new java.awt.Color(0, 0, 0));
        jFechaLabel.setText(oferta.getFecha());
        jPanel1.add(jFechaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, -1, -1));

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Cod.Oferta");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 147, -1, 19));

        jCodOfertaLabel.setBackground(new java.awt.Color(0, 0, 0));
        jCodOfertaLabel.setFont(new java.awt.Font("Roboto", 3, 14)); // NOI18N
        jCodOfertaLabel.setForeground(new java.awt.Color(0, 0, 0));
        jCodOfertaLabel.setText(oferta.getCod_oferta());
        jPanel1.add(jCodOfertaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));
        jPanel1.add(jEdicionTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 80, 30));

        jCantidadTextField.setText(String.valueOf(oferta.getCantidad()));
        jCantidadTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCantidadTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(jCantidadTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 90, 30));

        jLabel13.setBackground(new java.awt.Color(0, 153, 51));
        jLabel13.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 51));
        jLabel13.setText("€");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCantidadTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCantidadTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCantidadTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JDniArbitroLabel;
    private javax.swing.JTextField jCantidadTextField;
    private javax.swing.JLabel jCodOfertaLabel;
    private javax.swing.JTextField jEdicionTextField;
    private javax.swing.JLabel jFechaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}