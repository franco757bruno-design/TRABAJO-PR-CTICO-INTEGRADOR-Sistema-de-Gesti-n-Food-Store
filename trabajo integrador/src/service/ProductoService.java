/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Categoria;
import entities.Producto;
import exception.StockInvalidoException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class ProductoService {
    private ArrayList<Producto> productos;
    private Long generadorId;
    
    public ProductoService() {
        this.productos = new ArrayList<>();
        this.generadorId = 1L;
    }    
    public void crearProducto(String nombre, String descripcion, String imagen, Double precio, int stock, boolean disponible, Categoria categoria) {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new StockInvalidoException("Error: El precio y el stock no pueden ser números negativos");
        }
        
        if (precio < 0 || stock < 0) {
            System.out.println("Error: El precio y el stock no pueden ser números negativos.");
            return;
        }
        
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("Error: La categoría seleccionada no existe o fue eliminada.");
            return;
        }

        Producto nuevoProducto = new Producto(nombre, descripcion, imagen, precio, stock, disponible, categoria, generadorId, false, LocalDateTime.now());
        productos.add(nuevoProducto);
        categoria.agregarProducto(nuevoProducto); 
        System.out.println("Éxito: Producto '" + nombre + "' creado correctamente con el ID: " + generadorId);
        generadorId++;
    }
    
    public void listarProductos() {
        System.out.println("LIsta de productos");
        boolean hayProductos = false;

        for (Producto prod : productos) {
            if (!prod.isEliminado()) { 
                System.out.println("\nID: " + prod.getId() + 
                                   "\n | Nombre: " + prod.getNombre() + 
                                   "\n | Precio: $" + prod.getPrecio() + 
                                   "\n | Stock: " + prod.getStock() + 
                                   "\n | Categoría: " + prod.getCategoria().getNombre());
                hayProductos = true;
            }
        }

        if (!hayProductos) {
            System.out.println("No hay productos cargados.");
        }
    }
    
    public Producto buscarPorId(Long id) {
        for (Producto prod : productos) {
            if (!prod.isEliminado() && prod.getId().equals(id)) {
                return prod;
            }
        }
        return null;
    }
    
    public void editarProducto(Long id, String nuevoNombre, Double nuevoPrecio, int nuevoStock, Categoria nuevaCategoria) {
        Producto prod = buscarPorId(id); // Buscamos por ID [cite: 604]
        
        if (prod != null) {
            if (nuevoPrecio != null && nuevoPrecio < 0) {
                throw new StockInvalidoException("Error: El nuevo precio no puede ser negativo.");
            }
            if (nuevoStock < 0) {
                throw new StockInvalidoException("Error: El nuevo stock no puede ser negativo.");
            }
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                prod.setNombre(nuevoNombre);
            }
            if (nuevoPrecio != null) {
                prod.setPrecio(nuevoPrecio);
            }
            prod.setStock(nuevoStock);
            
            if (nuevaCategoria != null && !nuevaCategoria.isEliminado()) {
                prod.setCategoria(nuevaCategoria);
            }
            System.out.println("Éxito: Producto actualizado correctamente.");
            } else {
            System.out.println("Error: No se encontró ningún producto activo con el ID " + id);
        }
    }
    
    public void eliminarProducto(Long id) {
        Producto prod = buscarPorId(id);
        if (prod != null) {
            prod.setEliminado(true); 
            System.out.println("Éxito: Producto eliminado del sistema.");
        } else {
            System.out.println("Error: No se encontró ningún producto activo con el ID " + id);
        }
    }
    
    
}
