package com.utn.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor

// No usamos @AllArgsConstructor porque la colección debe inicializarse vacía

public class Categoria extends Base {
    private String nombre;
    private String descripcion;
    @Builder.Default
    private Set<Producto> productos = new HashSet<>();
}
