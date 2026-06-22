/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author franc
 */
public class DetallePedido extends Base{
    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto, Long id, boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = this.calcularSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    public double calcularSubtotal() {
        return cantidad * producto.getPrecio();
    }
    
    @Override
    public String toString() {
    // Formato: - DetallePedido #[id]: [producto] x [cantidad] => Subtotal: $[valor] [cite: 1078]
    return " - DetallePedido #" + this.getId() + ": " + this.producto.getNombre() + 
           " x " + this.cantidad + " => Subtotal: $" + this.subtotal;
}
    
}
