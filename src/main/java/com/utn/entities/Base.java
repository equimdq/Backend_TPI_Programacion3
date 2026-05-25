package com.utn.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass // Comparte columnas con las entidades hijas, pero no crea tabla Base
@Getter
@Setter
@ToString
@NoArgsConstructor // Constructor vacio requerido por JPA/Hibernate
@AllArgsConstructor
@SuperBuilder
public abstract class Base {
    @Id // Clave primaria de cada entidad hija.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La DB genera el id al persistir
    private Long id;

    @Builder.Default
    private boolean eliminado = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // Fecha/hora al crear el objeto en memoria

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Base base = (Base) o;
        // Dos entidades nuevas con id null no son automáticamente la misma entidad
        return id != null && id.equals(base.id);
    }

    @Override
    public int hashCode() {
        // No usamos id aca porque cambia de null a valor real cuando la DB lo genera
        return getClass().hashCode();
    }
}
