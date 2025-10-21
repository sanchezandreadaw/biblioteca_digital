package com.biblioteca.biblioteca_digital.dtos;

import java.time.LocalDate;

import com.biblioteca.biblioteca_digital.enums.GeneroLibro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateLecturaDTO {

    private Long id;

    @NotBlank(message = "El título del libro es requerido.")
    private String titulo;

    @NotBlank(message = "El autor del libro es requerido.")
    private String autor;

    @NotNull(message = "El género del libro es requerido.")
    private GeneroLibro generoLibro;

    @NotNull(message = "La fecha en la que comenzaste el libro es requerida.")
    private LocalDate fechaInicio;

    @NotNull(message = "La fechaaa en la que finalizaste el libro es requerida")
    private LocalDate fechaFin;

    public UpdateLecturaDTO() {
    }

    public UpdateLecturaDTO(Long id, String titulo, String autor, GeneroLibro generoLibro, LocalDate fechaInicio,
            LocalDate fechaFin) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.generoLibro = generoLibro;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public GeneroLibro getGeneroLibro() {
        return generoLibro;
    }

    public void setGeneroLibro(GeneroLibro generoLibro) {
        this.generoLibro = generoLibro;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

}
