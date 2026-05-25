package com.utn;

import com.utn.entities.Categoria;
import com.utn.entities.Pedido;
import com.utn.entities.Producto;
import com.utn.entities.Usuario;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.enums.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();

        Long usuarioIdBuscado;
        String mailBuscado;
        Long producto1Id;
        Long producto2Id;
        Long productoBorrarId;
        String sufijoEjecucion = String.valueOf(System.currentTimeMillis());

        try {
            em.getTransaction().begin();

            Categoria catPizzas = Categoria.builder()
                    .nombre("Pizzas")
                    .descripcion("Pizzas artesanales")
                    .build();

            Categoria catBebidas = Categoria.builder()
                    .nombre("Bebidas")
                    .descripcion("Bebidas frescas")
                    .build();

            Categoria catBurgers = Categoria.builder()
                    .nombre("Hamburguesas")
                    .descripcion("Hamburguesas de diferentes estilos")
                    .build();

            Producto p1 = crearProducto("Pizza Muzzarella", 4500.0, "Salsa y muzzarella", 20, "muzza.jpg");
            Producto p2 = crearProducto("Coca Cola", 2500.0, "Coca Cola fresca", 20, "coca.jpg");
            Producto p3 = crearProducto("Hamburguesa", 10500.0, "Hamburguesa de carne vacuna", 20, "hamburguesa.jpg");
            Producto p4 = crearProducto("Cerveza Quilmes", 3500.0, "Lata 473ml bien helada", 50, "quilmes.jpg");
            Producto p5 = crearProducto("Empanadas de Carne", 1200.0, "Carne cortada a cuchillo por unidad", 100, "empanadas.jpg");
            Producto p6 = crearProducto("Papas Fritas", 5500.0, "Porcion grande con sal", 15, "papas.jpg");
            Producto p7 = crearProducto("Agua Mineral", 1800.0, "500ml sin gas", 30, "agua.jpg");
            Producto p8 = crearProducto("Pizza Napolitana", 5200.0, "Muzzarella, tomate y ajo", 10, "napo.jpg");
            Producto p9 = crearProducto("Sandwich de Milanesa", 8500.0, "Lechuga, tomate y mayonesa", 12, "mila.jpg");
            Producto p10 = crearProducto("Flan con Dulce", 3000.0, "Casero con dulce de leche", 8, "flan.jpg");

            catPizzas.addProducto(p1);
            catBebidas.addProducto(p2);
            catBurgers.addProducto(p3);
            catBebidas.addProducto(p4);
            catPizzas.addProducto(p5);
            catBurgers.addProducto(p6);
            catBebidas.addProducto(p7);
            catPizzas.addProducto(p8);
            catBurgers.addProducto(p9);
            catPizzas.addProducto(p10);

            List<Categoria> categorias = List.of(catPizzas, catBebidas, catBurgers);
            List<Producto> productos = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);

            categorias.forEach(em::persist);
            productos.forEach(em::persist);

            Usuario u1 = Usuario.builder()
                    .nombre("Ezequiel")
                    .apellido("Ventura")
                    // Mail unico por ejecucion para que la busqueda JPQL devuelva un solo Usuario.
                    .mail("eze+" + sufijoEjecucion + "@mail.com")
                    .celular("223555")
                    .rol(Rol.ADMIN)
                    .build();

            Usuario u2 = Usuario.builder()
                    .nombre("Juan")
                    .apellido("Perez")
                    .mail("juan+" + sufijoEjecucion + "@mail.com")
                    .celular("223444")
                    .rol(Rol.USUARIO)
                    .build();

            em.persist(u1);
            em.persist(u2);

            Pedido ped1 = crearPedido(Estado.PENDIENTE, FormaPago.EFECTIVO);
            ped1.addDetallePedido(2, p1);
            ped1.addDetallePedido(1, p2);
            ped1.calcularTotal();

            Pedido ped2 = crearPedido(Estado.CONFIRMADO, FormaPago.TRANSFERENCIA);
            ped2.addDetallePedido(1, p3);
            ped2.addDetallePedido(1, p6);
            ped2.calcularTotal();

            Pedido ped3 = crearPedido(Estado.TERMINADO, FormaPago.TARJETA);
            ped3.addDetallePedido(3, p5);
            ped3.addDetallePedido(1, p4);
            ped3.calcularTotal();

            u1.addPedido(ped1);
            u1.addPedido(ped2);
            u2.addPedido(ped3);

            // Cascade en Pedido.detalles persiste automáticamente los DetallePedido
            em.persist(ped1);
            em.persist(ped2);
            em.persist(ped3);

            em.getTransaction().commit();

            usuarioIdBuscado = u1.getId();
            mailBuscado = u1.getMail();
            producto1Id = p1.getId();
            producto2Id = p2.getId();
            productoBorrarId = p10.getId(); // No está referenciado por ningún DetallePedido

            System.out.println("\n 4 Instanciar y persistir");
            System.out.println("Usuarios persistidos: 2 -> ids " + u1.getId() + ", " + u2.getId());
            System.out.println("Pedidos persistidos: 3 -> ids " + ped1.getId() + ", " + ped2.getId() + ", " + ped3.getId());
            System.out.println("Detalles por pedido: " + ped1.getDetalles().size() + ", " + ped2.getDetalles().size() + ", " + ped3.getDetalles().size());
            System.out.println("Categorías persistidas: " + categorias.size());
            System.out.println("Productos persistidos: " + productos.size());
            System.out.println("Totales pedidos: " + ped1.getTotal() + ", " + ped2.getTotal() + ", " + ped3.getTotal());

            em.clear(); // Limpiamos el contexto para los próximos find

            em.getTransaction().begin();


            Producto productoParaActualizar1 = em.find(Producto.class, producto1Id);
            Producto productoParaActualizar2 = em.find(Producto.class, producto2Id);

            productoParaActualizar1.setPrecio(4800.0);
            productoParaActualizar1.setStock(18);
            productoParaActualizar2.setPrecio(2700.0);
            productoParaActualizar2.setStock(25);


            em.getTransaction().commit();

            System.out.println("\n 5 Actualizar Productos");
            System.out.println(productoParaActualizar1.getNombre() + " -> precio " + productoParaActualizar1.getPrecio() + ", stock " + productoParaActualizar1.getStock());
            System.out.println(productoParaActualizar2.getNombre() + " -> precio " + productoParaActualizar2.getPrecio() + ", stock " + productoParaActualizar2.getStock());

            em.clear();

            Usuario usuarioPorId = em.find(Usuario.class, usuarioIdBuscado);

            System.out.println("\n 6 Buscar un usuario por ID");
            System.out.println("ID buscado: " + usuarioIdBuscado);
            System.out.println("Resultado: " + usuarioPorId.getNombre() + " " + usuarioPorId.getApellido() + " - " + usuarioPorId.getMail());

            Usuario usuarioPorMail = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.mail = :mail",
                            Usuario.class
                    )
                    .setParameter("mail", mailBuscado)
                    .getSingleResult();

            System.out.println("\n 7 Buscar un usuario por mail ");
            System.out.println("Mail buscado: " + mailBuscado);
            System.out.println("Resultado: id " + usuarioPorMail.getId() + " - " + usuarioPorMail.getNombre() + " " + usuarioPorMail.getApellido());

            em.getTransaction().begin();


            Producto productoABorrar = em.find(Producto.class, productoBorrarId);
            System.out.println("\n 8 Borrar un producto ");
            System.out.println("Producto a borrar: id " + productoABorrar.getId() + " - " + productoABorrar.getNombre());

            em.remove(productoABorrar);
            em.getTransaction().commit();

            em.clear();
            Producto productoBorrado = em.find(Producto.class, productoBorrarId);
            System.out.println("Verificación post-delete: " + (productoBorrado == null ? "producto eliminado" : "producto todavía existe"));
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    private static Producto crearProducto(String nombre, double precio, String descripcion, int stock, String imagen) {
        return Producto.builder()
                .nombre(nombre)
                .precio(precio)
                .descripcion(descripcion)
                .stock(stock)
                .imagen(imagen)
                .disponible(true)
                .build();
    }

    private static Pedido crearPedido(Estado estado, FormaPago formaPago) {
        return Pedido.builder()
                .fecha(LocalDate.now())
                .estado(estado)
                .formaPago(formaPago)
                .build();
    }
}
