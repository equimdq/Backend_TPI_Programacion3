package com.utn.entities;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor

// No usamos @AllArgsConstructor porque la colección debe inicializarse vacía

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    @Builder.Default // le comunicamos a Lombok que cuando use el builder,
    // use este valor por defecto si no se especifica otro
    private Set<DetallePedido> detalles = new HashSet<>();

    // Consigna 1 (Programación Funcional)
    @Override
    public void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido detalle = DetallePedido.builder()
                .id(producto.getId())
                .cantidad(cantidad)
                .producto(producto)
                .subtotal(cantidad * producto.getPrecio())
                .build();
        this.detalles.add(detalle);
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        // Invocamos al método de búsqueda definido en el UML
        DetallePedido detalle = findDetallePedidoByProducto(producto);
        // Verificamos que no sea null antes de borrar
        if (detalle != null) {
            this.detalles.remove(detalle);
        }
    }
}
