/*
DDSI -- PRÁCTICA 3 
Fecha : 07/01/2022
*/
package com.basados.seminario1.model;

public class Material {
    private String codMaterial , nombre , cifPatrocinador ;
    private Integer cantidad;

    public Material(String codMaterial, String nombre, Integer cantidad, String cifPatrocinador) {
        this.codMaterial = codMaterial;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cifPatrocinador = cifPatrocinador;
    }

    public String getCodMaterial() {
        return codMaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getCifPatrocinador() {
        return cifPatrocinador;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setCifPatrocinador(String cifPatrocinador) {
        this.cifPatrocinador = cifPatrocinador;
    }

    @Override
    public String toString() {
        return "Material{" + "codMaterial=" + codMaterial + ", nombre=" + nombre + ", cantidad=" + cantidad + ", cifPatrocinador=" + cifPatrocinador + ", numEdicion=" + '}';
    }
}
