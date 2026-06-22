/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PedidoService {
    private ArrayList<Pedido> pedidos;
    private Long generadorId;

    public PedidoService() {
        this.pedidos = new ArrayList<>();
        this.generadorId = 1L;
    }
    public void crearPedido(Usuario usuario, FormaPago formaPago, ArrayList<Producto> productosElegidos, ArrayList<Integer> cantidades) {
        if (usuario == null || usuario.isEliminado()) {
            System.out.println("Error: El usuario no es válido o está eliminado.");
            return;
        }
        Pedido nuevoPedido = new Pedido(LocalDate.now(), Estado.PENDIENTE, formaPago, usuario, generadorId, false, LocalDateTime.now());
        try {
            for (int i = 0; i < productosElegidos.size(); i++) {
                Producto p = productosElegidos.get(i);
                int cantidadPedida = cantidades.get(i);
                if (p.getStock() < cantidadPedida) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + p.getNombre());
                }
                p.setStock(p.getStock() - cantidadPedida);
                nuevoPedido.addDetallePedido(cantidadPedida, p);
            }
            usuario.agregarPedido(nuevoPedido);
            pedidos.add(nuevoPedido);
            
            System.out.println("Éxito: Pedido #" + generadorId + " creado. Total a pagar: $" + nuevoPedido.getTotal());
            generadorId++;

        } catch (Exception e) {
            System.out.println("Operación cancelada: " + e.getMessage());
        }
    }

    public void listarPedidos() {
        System.out.println("Lista de Pedidos");
        boolean hayPedidos = false;

        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                System.out.println("\n Pedido #" + p.getId() + 
                                   "\n | Fecha: " + p.getFecha() + 
                                   "\n | Usuario: " + p.getUsuario().getNombre() + " " + p.getUsuario().getApellido() +
                                   "\n | Estado: " + p.getEstado() + 
                                   "\n | Pago: " + p.getFormaPago() + 
                                   "\n | Total: $" + p.getTotal());
                hayPedidos = true;
            }
        }

        if (!hayPedidos) {
            System.out.println("No hay pedidos registrados.");
        }
    }
    
    public Pedido buscarPorId(Long id) {
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void actualizarPedido(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) {
        Pedido p = buscarPorId(id);
        if (p != null) {
            if (nuevoEstado != null) {
                p.setEstado(nuevoEstado);
            }
            if (nuevaFormaPago != null) {
                p.setFormaPago(nuevaFormaPago);
            }
            System.out.println("Éxito: Pedido actualizado a estado " + p.getEstado());
        } else {
            System.out.println("Error: No se encontró ningún pedido activo con el ID " + id);
        }
    }

    public void eliminarPedido(Long id) {
        Pedido p = buscarPorId(id);
        if (p != null) {
            p.setEliminado(true);
            System.out.println("Éxito: Pedido eliminado del sistema.");
        } else {
            System.out.println("Error: No se encontró ningún pedido activo con el ID " + id);
        }
    }
}
