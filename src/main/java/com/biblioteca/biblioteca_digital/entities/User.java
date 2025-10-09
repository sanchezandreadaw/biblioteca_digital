package com.biblioteca.biblioteca_digital.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "Los apellidos son requeridos")
    private String apellidos;

    @NotBlank(message = "El correo es requerido")
    @Column(unique = true)
    private String correo;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Libro> libros;
}
