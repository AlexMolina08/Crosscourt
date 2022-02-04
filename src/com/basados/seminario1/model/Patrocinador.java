/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
 */
package com.basados.seminario1.model;

/**
 *
 * Clase Modelo patrocinador
 */
public class Patrocinador {
    
    /*atributos de un patrocinador*/
    private String cif , contacto , tlf , email;
    private int num_edicion; // numero de edicion en la que participa el patrocinador
    private Float dinero_aportado;

    public Patrocinador(String cif, String contacto, String tlf, String email ,
            Float dinero_aportado , int num_edicion) {
        this.cif = cif;
        this.contacto = contacto;
        this.tlf = tlf;
        this.email = email;
        this.dinero_aportado = dinero_aportado;
        this.num_edicion = num_edicion;
    }

    public float getDinero_aportado() {
        return dinero_aportado;
    }

    public int getNum_edicion() {
        return num_edicion;
    }
    
    public String getCif() {
        return cif;
    }

    public String getContacto() {
        return contacto;
    }


    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTlf() {
        return tlf;
    }

    public String getEmail() {
        return email;
    }
    
    
}
