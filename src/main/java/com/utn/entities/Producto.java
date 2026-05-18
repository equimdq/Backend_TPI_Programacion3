package com.utn.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)

public class Producto extends Base {

    @EqualsAndHashCode.Include
    @Override
    public Long getId() { return super.getId(); }
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
}
