package com.utn.entities;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity // Esta clase se persiste como una tabla.
@SuperBuilder
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor

// No usamos @AllArgsConstructor porque la colección debe inicializarse vacía

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;

    @Enumerated(EnumType.STRING) // Guarda el nombre del enum, no su posicion numerica.
    private Estado estado;
    private Double total;

    @Enumerated(EnumType.STRING) // Evita que cambiar el orden del enum rompa datos existentes.
    private FormaPago formaPago;
    @ManyToOne // Muchos pedidos pueden pertenecer a un mismo usuario
    @JoinColumn(name = "usuario_id") // FK en la tabla pedido
    @ToString.Exclude // Uso de Exclude para evitar recursión
    private Usuario usuario;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    // Pedido no tiene la FK; DetallePedido.pedido maneja la relación
    // Cascade propaga persist/update/delete a los detalles
    // orphanRemoval borra de la DB un detalle removido de esta colección
    @Builder.Default // le comunicamos a Lombok que cuando use el builder, use este valor por defecto si no se especifica otro
    @ToString.Exclude
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
                // IMPORTANTE: No seteamos id. DetallePedido tiene identidad propia y la DB la genera
                .cantidad(cantidad)
                .producto(producto)
                .subtotal(cantidad * producto.getPrecio())
                .build();
        addDetallePedido(detalle);
    }
    // Helper
    public void addDetallePedido(DetallePedido detalle) {
        this.detalles.add(detalle);
        detalle.setPedido(this); // Sincroniza el lado owner, donde vive la FK pedido_id.
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
            detalle.setPedido(null); // Al quedar huerfano, orphanRemoval permite borrarlo en la BD
        }
    }
}
