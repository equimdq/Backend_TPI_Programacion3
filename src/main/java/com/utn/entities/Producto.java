package com.utn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity // Esta clase se persiste como una tabla
@Getter
@Setter
@ToString(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class Producto extends Base {
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;

    @ManyToOne // Muchos productos pueden pertenecer a una misma categoría
    @JoinColumn(name = "categoria_id") // FK en la tabla producto
    @ToString.Exclude // Evita recursion al imprimir Producto -> Categoria -> Productos
    private Categoria categoria;
}
