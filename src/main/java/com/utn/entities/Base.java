package com.utn.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder


public abstract class Base {
    @EqualsAndHashCode.Include // NOTA: Se usa producto.getId() como id del DetallePedido para garantizar
    // unicidad en el HashSet. Sin esto, dos detalles podían tener el mismo id
    // y el Set los trataba como duplicados, descartando el segundo detalle.
    private Long id;
    @Builder.Default
    private boolean eliminado = false;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}