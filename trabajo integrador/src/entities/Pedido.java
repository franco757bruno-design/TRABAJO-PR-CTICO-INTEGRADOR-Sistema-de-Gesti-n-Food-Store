/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private ArrayList<DetallePedido> detalles;

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario, Long id, boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.fecha = fecha;
        this.estado = estado;
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    
    public void addDetallePedido(int cantidad, Producto producto){
        DetallePedido nuevo = new DetallePedido(cantidad, producto, Long.MIN_VALUE, true, LocalDateTime.MAX);
        detalles.add(nuevo);
        this.calcularTotal();
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto p){
        for (DetallePedido detalle : detalles) {
            if(detalle.getProducto().getId() == p.getId()){
                return detalle;
            }
        }return null;
            }
    
    public void deleteDetallePedidoByProducto(Producto producto){
        DetallePedido buscado = findDetallePedidoByProducto(producto);
        if (buscado != null ){
        detalles.remove(buscado);
        this.calcularTotal();
        }
    }
    
    @Override
    public String toString() {
    // Formato: > Pedido #[id] | Fecha: [fecha] | Estado: [estado] | FormaPago: [fp] [cite: 1077]
    StringBuilder sb = new StringBuilder();
    sb.append("\n> Pedido #").append(this.getId())
      .append(" | Fecha: ").append(this.fecha)
      .append(" | Estado: ").append(this.estado)
      .append(" | FormaPago: ").append(this.formaPago).append("\n");
    sb.append("--------------------------------------------------\n");
    
    // Recorremos los detalles y aprovechamos su propio toString()
    for (DetallePedido dp : detalles) {
        sb.append(dp.toString()).append("\n");
    }
    
    sb.append("TOTAL DEL PEDIDO: $").append(this.total).append("\n");
    sb.append("--------------------------------------------------\n");
    return sb.toString();
}

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido detalle : detalles) {
            this.total += detalle.getSubtotal();
        }
    }
    
}
