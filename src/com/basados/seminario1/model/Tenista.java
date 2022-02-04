/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.model;


/**
 *
 * Modelo de un tenista
 * 
 */

public class Tenista {
    
    private String dni,nombre,apellidos;
    private Integer numEdicion , posicionRanking;

    public Tenista(String dni, String nombre, String apellidos, Integer numEdicion, Integer posicionRanking) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numEdicion = numEdicion;
        this.posicionRanking = posicionRanking;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Integer getNumEdicion() {
        return numEdicion;
    }

    public Integer getPosicionRanking() {
        return posicionRanking;
    }

    @Override
    public String toString() {
        return "Tenista{" + "dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", numEdicion=" + numEdicion + ", posicionRanking=" + posicionRanking + '}';
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNumEdicion(Integer numEdicion) {
        this.numEdicion = numEdicion;
    }

    public void setPosicionRanking(Integer posicionRanking) {
        this.posicionRanking = posicionRanking;
    }
   
    
    
        
   
    
}
