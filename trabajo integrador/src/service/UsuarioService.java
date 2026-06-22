/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Usuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UsuarioService {
    private ArrayList<Usuario> usuarios;
    private Long generadorId;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.generadorId = 1L;
    }

    public void crearUsuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        if (mail == null || mail.trim().isEmpty()) {
            System.out.println("Error: El mail no puede estar vacío.");
            return;
        }
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                System.out.println("Error: El mail '" + mail + "' ya se encuentra registrado en el sistema.");
                return;
            }
        }
        Usuario nuevoUsuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rol, generadorId, false, LocalDateTime.now());
        
        usuarios.add(nuevoUsuario);
        System.out.println("Éxito: Usuario '" + nombre + " " + apellido + "' creado correctamente con el ID: " + generadorId);
        generadorId++;
    }

    public void listarUsuarios() {
        System.out.println("Listar ususrios");
        boolean hayUsuarios = false;

        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                System.out.println("\n ID: " + u.getId() + 
                                   "\n | Nombre: " + u.getNombre() + " " + u.getApellido() + 
                                   "\n | Mail: " + u.getMail() + 
                                   "\n | Rol: " + u.getRol());
                hayUsuarios = true;
            }
        }

        if (!hayUsuarios) {
            System.out.println("No hay usuarios cargados en el sistema.");
        }
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public void editarUsuario(String nuevoNombre, String nuevoApellido, String nuevoMail, String nuevoCelular, String nuevaContrasenia, Rol nuevoRol, Long id){
        Usuario u = buscarPorId(id);
        if (u != null) {
            if (nuevoMail != null && !nuevoMail.trim().isEmpty() && !nuevoMail.equalsIgnoreCase(u.getMail())) {
                for (Usuario otroUsuario : usuarios) {
                    if (!otroUsuario.isEliminado() && !otroUsuario.getId().equals(id) && otroUsuario.getMail().equalsIgnoreCase(nuevoMail)) {
                        System.out.println("Error: El mail '" + nuevoMail + "' ya está siendo usado por otro usuario.");
                        return;
                    }
                }
                u.setMail(nuevoMail);
            }
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()){ 
                u.setNombre(nuevoNombre);
            }
            if (nuevoApellido != null && !nuevoApellido.trim().isEmpty()){ 
                u.setApellido(nuevoApellido);
            }
            if (nuevoCelular != null && !nuevoCelular.trim().isEmpty()){ 
                u.setCelular(nuevoCelular);
            }
            if (nuevaContrasenia != null && !nuevaContrasenia.trim().isEmpty()){ 
                u.setContrasenia(nuevaContrasenia);
            }
            if (nuevoRol != null){ 
                u.setRol(nuevoRol);
            }

            System.out.println("Éxito: Usuario actualizado correctamente.");
        } else {
            System.out.println("Error: No se encontró ningún usuario activo con el ID " + id);
        }
    }

    public void eliminarUsuario(Long id) {
        Usuario u = buscarPorId(id);

        if (u != null) {
            u.setEliminado(true);
            System.out.println("Éxito: Usuario eliminado del sistema.");
        } else {
            System.out.println("Error: No se encontró ningún usuario activo con el ID " + id);
        }
    }
}
