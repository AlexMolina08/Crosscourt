/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.model;
/**
 * 
 *
 * Modelo de un árbitro
 * 
 */
public class Arbitro {
    private String dni , nombre , apellidos , correo;
    private int numEdicion ;  // edicion en la que trabaja

    public Arbitro(String dni, String nombre, String apellidos, String correo, int numEdicion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.numEdicion = numEdicion;
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

    public String getCorreo() {
        return correo;
    }

    public int getNumEdicion() {
        return numEdicion;
    }

    @Override
    public String toString() {
        return "Arbitro{" + "dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", correo=" + correo + ", numEdicion=" + numEdicion + '}';
    }
    
    
    
    
}
