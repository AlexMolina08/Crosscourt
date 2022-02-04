package com.basados.seminario1.model;

import java.util.Date;

/**
 *
 *  Modelo de un Pedido suministrado por un patrocinador identificado por su
 *  CIF
 * 
 */
public class Pedido {
    
    // Atributo de tipo Integer para poder inicializarlos a null en caso de estar
    // esperando a recibir respuesta o respuesta erronea
    
    // Codigo del pedido 
    private Integer cantidad , tipos;
    private String cod,cifPatrocinador , nombre , codMaterial;
    
    public Pedido(String cod, String codMaterial, String cifPatrocinador , Integer tipos , Integer cantidad) {
        this.cod = cod;
        this.codMaterial = codMaterial;
        this.cantidad = cantidad;
        this.tipos = tipos;
        this.cifPatrocinador = cifPatrocinador;
        this.nombre = nombre;
    }

    public String getCodMaterial() {
        return codMaterial;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setTipos(Integer tipos) {
        this.tipos = tipos;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setCifPatrocinador(String cifPatrocinador) {
        this.cifPatrocinador = cifPatrocinador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }

    
    
    public Integer getCantidad() {
        return cantidad;
    }

    public Integer getTipos() {
        return tipos;
    }


    public String getCifPatrocinador() {
        return cifPatrocinador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return "Pedido{" + "cantidad=" + cantidad + ", tipos=" + tipos + ", cod=" + cod + ", cifPatrocinador=" + cifPatrocinador + ", nombre=" + nombre + ", codMaterial=" + codMaterial + '}';
    }

    


    
    
    
    
}
