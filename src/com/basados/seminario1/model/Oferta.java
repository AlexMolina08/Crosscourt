/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.model;

import java.util.Date;

public class Oferta {
    private String cod_oferta , dniArbitro;
    private int numEdicion;
    private int aceptada;
    private double cantidad;
    private String fecha;

    public Oferta(String cod_oferta, String dniArbitro, int numEdicion , String fecha , double cantidad  , int aceptada ) {
        this.cod_oferta = cod_oferta;
        this.dniArbitro = dniArbitro;
        this.aceptada = aceptada;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.numEdicion = numEdicion;
    }

    public int getNum_edicion() {
        return numEdicion;
    }

    public void setNum_edicion(int num_edicion) {
        this.numEdicion = num_edicion;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    public Oferta(){
        this.cod_oferta = "";
        this.dniArbitro = "";
        this.aceptada = 0;
        this.cantidad = 0;
    }
    
    

    public void setCod_oferta(String cod_oferta) {
        this.cod_oferta = cod_oferta;
    }

    public void setDniArbitro(String dniArbitro) {
        this.dniArbitro = dniArbitro;
    }

    public void setAceptada(int aceptada) {
        this.aceptada = aceptada;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
    
    
    public String getCod_oferta() {
        return cod_oferta;
    }

    public String getDniArbitro() {
        return dniArbitro;
    }

    public int isAceptada() {
        return aceptada;
    }

    public double getCantidad() {
        return cantidad;
    }
    

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    

    @Override
    public String toString() {
        return "Oferta{" + "cod_oferta=" + cod_oferta + ", dniArbitro=" + dniArbitro + ", aceptada=" + aceptada + ", cantidad=" + cantidad + '}';
    }
    
    
    
    
}
