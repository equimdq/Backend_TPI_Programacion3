package com.utn.entities;

import com.utn.enums.Rol;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper=true)

// No usamos @AllArgsConstructor porque la colección debe inicializarse vacía


public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    private Rol rol;
    @Builder.Default
    private Set<Pedido> pedidos = new HashSet<>();
}
