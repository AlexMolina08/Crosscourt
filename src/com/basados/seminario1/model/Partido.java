/*
DDSI -- PRÁCTICA 3 
Fecha : 19/01/2022
 */
package com.basados.seminario1.model;

/**
 *
 * @author Alex
 */
public class Partido {
    private String codPartido , dni1 , dni2 , dniArbitro , fecha;
    private int numPista;

    public Partido(String codPartido, String dniArbitro) {
        this.codPartido = codPartido;
        this.dniArbitro = dniArbitro;
    }

    public Partido(String codPartido, String dni1, String dni2, String dniArbitro, String fecha, int numPista) {
        this.codPartido = codPartido;
        this.dni1 = dni1;
        this.dni2 = dni2;
        this.dniArbitro = dniArbitro;
        this.fecha = fecha;
        this.numPista = numPista;
    }

    public String getCodPartido() {
        return codPartido;
    }

    public String getDni1() {
        return dni1;
    }

    public String getDni2() {
        return dni2;
    }

    public String getDniArbitro() {
        return dniArbitro;
    }

    public String getFecha() {
        return fecha;
    }

    public int getNumPista() {
        return numPista;
    }

    public void setCodPartido(String codPartido) {
        this.codPartido = codPartido;
    }

    public void setDni1(String dni1) {
        this.dni1 = dni1;
    }

    public void setDni2(String dni2) {
        this.dni2 = dni2;
    }

    public void setDniArbitro(String dniArbitro) {
        this.dniArbitro = dniArbitro;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNumPista(int numPista) {
        this.numPista = numPista;
    }

    @Override
    public String toString() {
        return "Partido{" + "codPartido=" + codPartido + ", dni1=" + dni1 + ", dni2=" + dni2 + ", dniArbitro=" + dniArbitro + ", fecha=" + fecha + ", numPista=" + numPista + '}';
    }
    
    
        
}
