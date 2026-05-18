package com.utn;

import com.utn.dtos.UsuarioDTO;
import com.utn.entities.*;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.enums.Rol;

import java.time.LocalDate;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        // Categorías

        Categoria catPizzas = Categoria.builder()
                .id(1L)
                .nombre("Pizzas")
                .descripcion("Pizzas artesanales")
                .build();

        Categoria catBebidas = Categoria.builder()
                .id(2L)
                .nombre("Bebidas")
                .descripcion("Bebidas frescas")
                .build();

        Categoria catBurgers = Categoria.builder()
                .id(3L)
                .nombre("Hamburguesas")
                .descripcion("Hamburguesas de diferentes estilos")
                .build();

        // Productos

        Producto p1 = Producto.builder()
                .id(1L)
                .nombre("Pizza Muzzarella")
                .precio(4500.0)
                .descripcion("Salsa y muzzarella")
                .stock(20)
                .imagen("muzza.jpg")
                .disponible(true)
                .build();

        Producto p2 = Producto.builder()
                .id(2L)
                .nombre("Coca Cola")
                .precio(2500.0)
                .descripcion("Coca Cola fresca")
                .stock(20)
                .imagen("coca.jpg")
                .disponible(true)
                .build();

        Producto p3 = Producto.builder()
                .id(3L)
                .nombre("Hamburguesa")
                .precio(10500.0)
                .descripcion("Hamburguesa de carne vacuna")
                .stock(20)
                .imagen("hamburguesa.jpg")
                .disponible(true)
                .build();

        Producto p4 = Producto.builder()
                .id(4L)
                .nombre("Cerveza Quilmes")
                .precio(3500.0)
                .descripcion("Lata 473ml bien helada")
                .stock(50)
                .imagen("quilmes.jpg")
                .disponible(true)
                .build();

        Producto p5 = Producto.builder()
                .id(5L)
                .nombre("Empanadas de Carne")
                .precio(1200.0)
                .descripcion("Carne cortada a cuchillo (por unidad)")
                .stock(100)
                .imagen("empanadas.jpg")
                .disponible(true)
                .build();

        Producto p6 = Producto.builder()
                .id(6L)
                .nombre("Papas Fritas")
                .precio(5500.0)
                .descripcion("Porción grande con sal")
                .stock(15)
                .imagen("papas.jpg")
                .disponible(true)
                .build();

        Producto p7 = Producto.builder()
                .id(7L)
                .nombre("Agua Mineral")
                .precio(1800.0)
                .descripcion("500ml sin gas")
                .stock(30)
                .imagen("agua.jpg")
                .disponible(true)
                .build();

        Producto p8 = Producto.builder()
                .id(8L)
                .nombre("Pizza Napolitana")
                .precio(5200.0)
                .descripcion("Muzzarella, tomate y ajo")
                .stock(10)
                .imagen("napo.jpg")
                .disponible(true)
                .build();

        Producto p9 = Producto.builder()
                .id(9L)
                .nombre("Sándwich de Milanesa")
                .precio(8500.0)
                .descripcion("Lechuga, tomate y mayonesa")
                .stock(12)
                .imagen("mila.jpg")
                .disponible(true)
                .build();

        Producto p10 = Producto.builder()
                .id(10L)
                .nombre("Flan con Dulce")
                .precio(3000.0)
                .descripcion("Casero con dulce de leche")
                .stock(8)
                .imagen("flan.jpg")
                .disponible(true)
                .build();

        // Usuarios
        Usuario u1 = Usuario.builder()
                .id(1L).nombre("Ezequiel").apellido("Ventura")
                .mail("eze@mail.com").celular("223555").contraseña("admin123").rol(Rol.ADMIN)
                .build();

        Usuario u2 = Usuario.builder()
                .id(2L).nombre("Juan").apellido("Perez")
                .mail("juan@mail.com").celular("223444").contraseña("user123").rol(Rol.USUARIO)
                .build();

        // Pedidos
        Pedido ped1 = Pedido.builder().id(101L).fecha(LocalDate.now()).estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO).build();
        ped1.addDetallePedido(2, p1); // 2 Pizzas
        ped1.addDetallePedido(1, p2); // 1 Coca
        ped1.calcularTotal();

        Pedido ped2 = Pedido.builder().id(102L).fecha(LocalDate.now()).estado(Estado.CONFIRMADO)
                .formaPago(FormaPago.TRANSFERENCIA).build();
        ped2.addDetallePedido(1, p3); // 1 Burger
        ped2.addDetallePedido(1, p6); // 1 Papas
        ped2.calcularTotal();

        Pedido ped3 = Pedido.builder().id(103L).fecha(LocalDate.now()).estado(Estado.TERMINADO)
                .formaPago(FormaPago.TARJETA).build();
        ped3.addDetallePedido(3, p5); // 3 Empanadas
        ped3.addDetallePedido(1, p4); // 1 Cerveza
        ped3.calcularTotal();


        u1.getPedidos().add(ped1);
        u1.getPedidos().add(ped2);
        u2.getPedidos().add(ped3);

        // Lista
        List<Producto> listaProductos = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);

        System.out.println("--- Un Producto ---");
        System.out.println(p1.toString());

        System.out.println("--- Listado de Productos ---");
        listaProductos.forEach(p -> System.out.println(p.toString()));

        // Usuario con más pedidos (En este caso es u1 que tiene ped1 y ped2)
        System.out.println("--- Pedidos del usuario con más pedidos (Ezequiel) ---");
        u1.getPedidos().forEach(p -> System.out.println(p.toString()));


        // Comparación con Equals

        Producto pRepetido = Producto.builder()
                .id(1L) // Mismo ID que p1
                .nombre("Pizza Muzzarella")
                .build();

        System.out.println("--- Comparación Equals ---");
        for (Producto prod : listaProductos) {
            if (prod.equals(pRepetido)) {
                System.out.println("Se encontró un producto igual: " + prod.getNombre());
            }
        }

        UsuarioDTO dto = new UsuarioDTO(u1.getNombre(), u1.getApellido(), u1.getMail(), u1.getCelular());

        System.out.println("--- Información de Usuario (DTO - Seguro) ---");
        System.out.println(dto);


        // --- TP Programación Funcional---

        System.out.println("\n--- TP Programación Funcional ---");

        // Consigna 2
        System.out.println("---Consigna 2: Productos Disponibles ---");
        listaProductos.stream()
                .filter(Producto::getDisponible)
                .forEach(System.out::println);


        // Consigna 3

        System.out.println("--- Consigna 3: Cantidad de ítems en pedido 1 ---");
        int totalItems = ped1.getDetalles().stream()
                .mapToInt(DetallePedido::getCantidad)
                .sum();
        System.out.println("Items en pedido 1: " + totalItems);

        // Consigna 4

        System.out.println("--- Consigna 4: Productos menores a 5 en stock ---");
        List<Producto> productosBajoStock = listaProductos.stream()
                .filter(producto -> producto.getStock() < 5)
                .toList();

        if (productosBajoStock.isEmpty()) {
            System.out.println("No hay productos con stock menor a 5");
        }
        else  {
            productosBajoStock.forEach(System.out::println);
        }
    }

}


