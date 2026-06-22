/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajo.integrador;

import entities.Categoria;
import exception.StockInvalidoException;
import java.time.LocalDateTime;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TrabajoIntegrador {
        // Instanciamos los servicios de forma estática para poder usarlos en todo el Main
    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static PedidoService pedidoService = new PedidoService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        // Bucle del Menú Principal
        while (!salir) {
            System.out.println("SISTEMA DE PEDIDOS (FOOD STORE)");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer (muy importante después de leer un número)

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        System.out.println("En construcción...");
                        break;
                    case 3:
                        menuUsuarios();
                        System.out.println("En construcción...");
                        break;
                    case 4:
                        menuPedidos();
                        System.out.println("En construcción...");
                        break;
                    case 0:
                        salir = true;
                        System.out.println("Cerrando el sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar categorías");
            System.out.println("2. Crear categoría");
            System.out.println("3. Editar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        categoriaService.listarCategorias();
                        break;
                    case 2:
                        System.out.print("Ingrese el nombre de la categoría: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese la descripción: ");
                        String desc = scanner.nextLine();
                        categoriaService.crearCategoria(nombre, desc);
                        break;
                    case 3:
                        categoriaService.listarCategorias();
                        System.out.print("\nIngrese el ID de la categoría a editar: ");
                        Long idEditar = scanner.nextLong();
                        scanner.nextLine();
                        
                        System.out.print("Nuevo nombre (Enter para no modificar): ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nueva descripción (Enter para no modificar): ");
                        String nuevaDesc = scanner.nextLine();
                        
                        categoriaService.editarCategoria(idEditar, nuevoNombre, nuevaDesc);
                        break;
                    case 4:
                        categoriaService.listarCategorias();
                        System.out.print("\nIngrese el ID de la categoría a eliminar: ");
                        Long idEliminar = scanner.nextLong();
                        scanner.nextLine();
                        
                        System.out.print("¿Está seguro de eliminar esta categoría? (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            categoriaService.eliminarCategoria(idEliminar);
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un valor numérico.");
                scanner.nextLine();
            }
        }
    }
    // --- SUBMENÚ PRODUCTOS ---
    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        productoService.listarProductos();
                        break;
                    case 2:
                        try {
                            System.out.print("Nombre: ");
                            String nombre = scanner.nextLine();
                            System.out.print("Descripción: ");
                            String desc = scanner.nextLine();
                            System.out.print("Imagen (URL): ");
                            String img = scanner.nextLine();
                            System.out.print("Precio: ");
                            Double precio = scanner.nextDouble();
                            System.out.print("Stock: ");
                            int stock = scanner.nextInt();
                            System.out.print("¿Disponible? (true/false): ");
                            boolean disp = scanner.nextBoolean();
                            System.out.print("ID de la Categoría: ");
                            Long idCat = scanner.nextLong();
                            scanner.nextLine();

                            Categoria cat = categoriaService.buscarPorId(idCat);
                            if (cat != null) {
                                productoService.crearProducto(nombre, desc, img, precio, stock, disp, cat);
                            } else {
                                System.out.println("Error: Categoría no encontrada.");
                            }
                        } catch (StockInvalidoException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        productoService.listarProductos();
                        System.out.print("\nID del producto a editar: ");
                        Long idEditar = scanner.nextLong();
                        scanner.nextLine();
                        
                        System.out.print("Nuevo nombre (Enter para omitir): ");
                        String nNombre = scanner.nextLine();
                        System.out.print("Nuevo precio (-1 para omitir): ");
                        Double nPrecio = scanner.nextDouble();
                        System.out.print("Nuevo stock (-1 para omitir): ");
                        int nStock = scanner.nextInt();
                        System.out.print("Nuevo ID Categoría (-1 para omitir): ");
                        Long nIdCat = scanner.nextLong();
                        scanner.nextLine();
                        
                        Categoria nCat = null;
                        if (nIdCat != -1) {
                            nCat = categoriaService.buscarPorId(nIdCat);
                        }
                        
                        try {
                            productoService.editarProducto(idEditar, nNombre, nPrecio == -1 ? null : nPrecio, nStock == -1 ? -1 : nStock, nCat);
                        } catch (StockInvalidoException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("ID del producto a eliminar: ");
                        Long idEliminar = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) productoService.eliminarProducto(idEliminar);
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingreso inválido.");
                scanner.nextLine();
            }
        }
    }
    // --- SUBMENÚ USUARIOS ---
    private static void menuUsuarios() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        usuarioService.listarUsuarios();
                        break;
                    case 2:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Mail: ");
                        String mail = scanner.nextLine();
                        System.out.print("Celular: ");
                        String celular = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String pass = scanner.nextLine();
                        System.out.print("Rol (1: ADMIN, 2: USUARIO): ");
                        int rolOpt = scanner.nextInt();
                        scanner.nextLine();
                        
                        enums.Rol rol = (rolOpt == 1) ? enums.Rol.ADMIN : enums.Rol.USUSARIO;
                        usuarioService.crearUsuario(nombre, apellido, mail, celular, pass, rol);
                        break;
                    case 3:
                        usuarioService.listarUsuarios();
                        System.out.print("\nID del usuario a editar: ");
                        Long idEditar = scanner.nextLong();
                        scanner.nextLine();
                        
                        System.out.print("Nuevo mail (Enter para omitir): ");
                        String nMail = scanner.nextLine();
                        System.out.print("Nuevo nombre (Enter para omitir): ");
                        String nNombre = scanner.nextLine();
                        System.out.print("Nuevo apellido (Enter para omitir): ");
                        String nApellido = scanner.nextLine();
                        System.out.print("Nuevo celular (Enter para omitir): ");
                        String nCelular = scanner.nextLine();
                        System.out.print("Nueva contraseña (Enter para omitir): ");
                        String nPass = scanner.nextLine();
                        
                        System.out.print("Nuevo Rol (1: ADMIN, 2: USUARIO, 0: Omitir): ");
                        int nrolOpt = scanner.nextInt();
                        scanner.nextLine();
                        
                        enums.Rol nRol = null;
                        if (nrolOpt == 1) {
                            nRol = enums.Rol.ADMIN;
                        } else if (nrolOpt == 2) {
                            nRol = enums.Rol.USUSARIO;
                        }
                        
                        // ¡Acá llamamos a TU método con TU orden exacto!
                        usuarioService.editarUsuario(nNombre, nApellido, nMail, nCelular, nPass, nRol, idEditar);
                        break;

                    case 4:
                        System.out.print("ID del usuario a eliminar: ");
                        Long idEliminar = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) usuarioService.eliminarUsuario(idEliminar);
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingreso numérico inválido.");
                scanner.nextLine();
            }
        }
    }
    // --- SUBMENÚ PEDIDOS ---
    private static void menuPedidos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar estado/pago");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        pedidoService.listarPedidos();
                        break;
                    case 2:
                        usuarioService.listarUsuarios();
                        System.out.print("\nID del Usuario que realiza el pedido: ");
                        Long idUsr = scanner.nextLong();
                        scanner.nextLine();
                        entities.Usuario usr = usuarioService.buscarPorId(idUsr);
                        
                        if (usr == null) {
                            System.out.println("Error: Usuario no válido.");
                            break;
                        }

                        System.out.print("Forma de pago (1: TARJETA, 2: TRANSFERENCIA, 3: EFECTIVO): ");
                        int fpOpt = scanner.nextInt();
                        scanner.nextLine();
                        enums.FormaPago fp = (fpOpt == 1) ? enums.FormaPago.TARJETA : (fpOpt == 2) ? enums.FormaPago.TRANSFERENCIA : enums.FormaPago.EFECTIVO;

                        // Listas paralelas para el carrito
                        java.util.ArrayList<entities.Producto> carritoProd = new java.util.ArrayList<>();
                        java.util.ArrayList<Integer> carritoCant = new java.util.ArrayList<>();
                        
                        boolean agregarMas = true;
                        while(agregarMas) {
                            productoService.listarProductos();
                            System.out.print("ID del producto a comprar: ");
                            Long idProd = scanner.nextLong();
                            System.out.print("Cantidad: ");
                            int cant = scanner.nextInt();
                            scanner.nextLine();
                            
                            entities.Producto p = productoService.buscarPorId(idProd);
                            if (p != null) {
                                carritoProd.add(p);
                                carritoCant.add(cant);
                                System.out.println("Producto agregado al carrito.");
                            } else {
                                System.out.println("Producto no encontrado.");
                            }
                            
                            System.out.print("¿Agregar otro producto? (S/N): ");
                            agregarMas = scanner.nextLine().equalsIgnoreCase("S");
                        }
                        
                        if (!carritoProd.isEmpty()) {
                            pedidoService.crearPedido(usr, fp, carritoProd, carritoCant);
                        } else {
                            System.out.println("El pedido fue cancelado porque no se agregaron productos.");
                        }
                        break;
                    case 3:
                        pedidoService.listarPedidos();
                        System.out.print("\nID del pedido a actualizar: ");
                        Long idActualizar = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("Nuevo Estado (1: PENDIENTE, 2: CONFIRMADO, 3: TERMINADO, 4: CANCELADO): ");
                        int estOpt = scanner.nextInt();
                        scanner.nextLine();
                        
                        enums.Estado est = null;
                        switch(estOpt){
                            case 1: est = enums.Estado.PENDIENTE; break;
                            case 2: est = enums.Estado.CONFIRMADO; break;
                            case 3: est = enums.Estado.TERMINADO; break;
                            case 4: est = enums.Estado.CANCELADO; break;
                        }
                        
                        pedidoService.actualizarPedido(idActualizar, est, null);
                        break;
                    case 4:
                        System.out.print("ID del pedido a eliminar: ");
                        Long idEliminar = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) pedidoService.eliminarPedido(idEliminar);
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingreso numérico inválido.");
                scanner.nextLine();
            }
        }
    }
    
    }
    
