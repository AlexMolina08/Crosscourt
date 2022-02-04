package com.basados.seminario1.model;

/**
 *
 *  Modelo de un Producto (Stock) 
 * 
 */
public class Stock {
    
    // Codigo del producto ( de tipo Integer para poder establecerlo a null )
    private Integer cod = null; 
    private Integer cantidad = null;

    public Stock(Integer cod, Integer cantidad) {
        this.cod = cod;
        this.cantidad = cantidad;
    }
    
    
    // Atributos de tipo Integer para poder inicializarlos a null en caso de estar
    // esperando a recibir respuesta o respuesta erronea
    public Stock() {
        this.cod = null;
        this.cantidad = null;
        
    }

    public Integer getCod() {
        return cod;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Stock{" + "cod=" + cod + ", cantidad=" + cantidad + '}';
    }
    
     
}
