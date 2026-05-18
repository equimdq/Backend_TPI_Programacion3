package com.utn.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;


    // Constructor manual para calcular el subtotal

    // NOTA: Se usa super() + setId() en lugar de super(id) porque al usar @SuperBuilder
    // en Base, el constructor con parámetros ya no está disponible directamente.
    // Se asigna el ID mediante el setter heredado de Base.

    @Tolerate // Se importa para que Lombok lo tolere sin generar conflictos
    public DetallePedido(Long id, int cantidad, Producto producto) {
        super();
        this.setId(id);
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = cantidad * producto.getPrecio();
    }
}
