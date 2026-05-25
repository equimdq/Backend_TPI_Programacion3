package com.utn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

@Entity // Esta clase se persiste como una tabla
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;

    @ManyToOne // Muchos detalles pueden referenciar el mismo producto
    @JoinColumn(name = "producto_id") // FK real en detalle_pedido hacia producto
    private Producto producto;

    @ManyToOne // Muchos detalles pertenecen a un mismo pedido
    @JoinColumn(name = "pedido_id") // FK en la tabla detalle_pedido
    @ToString.Exclude
    private Pedido pedido;

    // Constructor manual para calcular subtotal sin asignar id
    // El id identifica a esta línea de pedido y lo genera la DB
    @Tolerate
    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = cantidad * producto.getPrecio();
    }
}
