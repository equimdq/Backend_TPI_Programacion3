package com.utn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity// Persiste como tabla
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor


public class Categoria extends Base {
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria") // Categoria no tiene la FK, la maneja Producto.categoria
    @Builder.Default
    @ToString.Exclude // Evita recursion al imprimir Categoria -> Productos -> Categoria
    private Set<Producto> productos = new HashSet<>();

    // Helper para mantener sincronizados los dos lados de la relación bidireccional
    public void addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setCategoria(this);
    }

    public void removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setCategoria(null);
    }
}
