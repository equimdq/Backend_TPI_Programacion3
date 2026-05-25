package com.utn.entities;

import com.utn.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;


@Entity // Esta clase se persiste como una tabla.
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper=true)

// No usamos @AllArgsConstructor porque la colección debe inicializarse vacia


public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    @Enumerated(EnumType.STRING) // Guarda ADMIN/USUARIO como texto en vez de ordinal
    private Rol rol;
    @OneToMany(mappedBy = "usuario") // Usuario no tiene la FK; la relación la maneja Pedido.usuario
    @Builder.Default
    @ToString.Exclude // Uso de Exclude para evitar recursión infinita
    private Set<Pedido> pedidos = new HashSet<>();

    // Helper para mantener sincronizados los dos lados de la relación bidireccional
    public void addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setUsuario(this);
    }

    public void removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setUsuario(null);
    }
}
