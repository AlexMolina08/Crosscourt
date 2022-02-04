/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.basados.seminario1.model;

import java.util.Objects;

/**
 *
 * @author alex
 */
public class DetalleID{
    private Integer codPedido;
    private Integer codProducto;

    public DetalleID(Integer codPedido, Integer codProducto) {
        this.codPedido = codPedido;
        this.codProducto = codProducto;
    }

    public Integer getCodPedido() {
        return codPedido;
    }

    public Integer getCodProducto() {
        return codProducto;
    }

    public void setCodPedido(Integer codPedido) {
        this.codPedido = codPedido;
    }

    public void setCodProducto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DetalleID other = (DetalleID) obj;
        if (!Objects.equals(this.codPedido, other.codPedido)) {
            return false;
        }
        if (!Objects.equals(this.codProducto, other.codProducto)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "DetalleID{" + "codPedido=" + codPedido + ", codProducto=" + codProducto + '}';
    }
    
}
