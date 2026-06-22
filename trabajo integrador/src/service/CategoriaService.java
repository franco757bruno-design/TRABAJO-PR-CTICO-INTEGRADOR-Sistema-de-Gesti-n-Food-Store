/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Categoria;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class CategoriaService {
    private ArrayList<Categoria> categorias;
    private Long generadorId; 

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.generadorId = 1L;
    }
    public void crearCategoria(String nombre, String descripcion){
        if (nombre == null || nombre.trim().isEmpty()){
            System.out.println("Error: El nombre de la categoría no puede estar vacío.");
            return;
        }
        for (Categoria cat : categorias) {
            if(!cat.isEliminado() && cat.getNombre().equalsIgnoreCase(nombre)){
            System.out.println("Error: Ya existe una categoría activa con el nombre '" + nombre + "'.");
            return;
            }
        }
        Categoria nuevaCategoria = new Categoria(generadorId, false, LocalDateTime.now(), nombre, descripcion);
        categorias.add(nuevaCategoria);
        System.out.println("Éxito: Categoría '" + nombre + "' creada correctamente con el ID: " + generadorId);
        generadorId++;
        
    }
    
    public void listarCategorias() {
        System.out.println("Lista de categorias");
        boolean hayCategorias = false;

        for (Categoria cat : categorias) {
            if (!cat.isEliminado()) {
                System.out.println("ID: " + cat.getId() + " | Nombre: " + cat.getNombre() + " | Desc: " + cat.getDescripcion());
                hayCategorias = true;
            }
        }
            if (!hayCategorias) {
                System.out.println("No hay categorías cargadas.");
        }
    }
    
    public Categoria buscarPorId(Long id) {
        for (Categoria cat : categorias){
            if (!cat.isEliminado() && cat.getId().equals(id)) {
                return cat;
            }
        }
        return null;
    }
    
    public void editarCategoria(Long id, String nuevoNombre, String nuevaDescripcion) {
        Categoria cat = buscarPorId(id);
        if (cat != null) {
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                cat.setNombre(nuevoNombre);
            }
            if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
                cat.setDescripcion(nuevaDescripcion);
            }
            System.out.println("Éxito: Categoría actualizada correctamente.");
        } else {
            System.out.println("Error: No se encontró ninguna categoría activa con el ID " + id);
        }
    }
    
    public void eliminarCategoria(Long id) {
        Categoria cat = buscarPorId(id);
        if (cat != null) {
            cat.setEliminado(true);                                              
            System.out.println("Éxito: Categoría eliminada del sistema.");
        } else {
            System.out.println("Error: No se encontró ninguna categoría activa con el ID " + id);
        }
    }
}
