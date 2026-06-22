/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.Rol;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private ArrayList<Pedido> pedidos; 

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol, Long id, boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = pedidos;
        this.pedidos = new ArrayList<>();
    }
    
    public void agregarPedido(Pedido p) {
        pedidos.add(p);
    }
    
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("==================================================\n");
    sb.append("USUARIO: ").append(this.nombre).append(" ").append(this.apellido)
      .append(" | Mail: ").append(this.mail)
      .append(" | Rol: ").append(this.rol).append("\n");
    sb.append("==================================================\n");
    
    double totalAcumulado = 0.0;

    for (Pedido p : pedidos) {
        sb.append(p.toString());
        totalAcumulado += p.getTotal();
    }
    
    sb.append("TOTAL ACUMULADO del usuario: $").append(totalAcumulado).append("\n");
    sb.append("==================================================\n");
    
    return sb.toString();
}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
}
