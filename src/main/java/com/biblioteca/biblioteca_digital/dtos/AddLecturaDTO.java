package com.biblioteca.biblioteca_digital.dtos;

import java.time.LocalDate;

import com.biblioteca.biblioteca_digital.enums.GeneroLibro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddLecturaDTO {

    private Long idUsuario;

    @NotBlank(message = "El título es obligatorio.")
    private String titulo;

    @NotBlank(message = "El nombre del autor es obligatorio.")
    private String autor;

    @NotNull(message = "El género del libro es obligatorio.")
    private GeneroLibro generoLibro;

    @NotNull(message = "La fecha en la que comenzaste el libro es obligatoria.")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha en la que finalizaste el libro es obligatoria.")
    private LocalDate fechaFin;

    public AddLecturaDTO() {
    }

    public AddLecturaDTO(Long idUsuario, String titulo, String autor, GeneroLibro generoLibro, LocalDate fechaInicio,
            LocalDate fechaFin) {
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.autor = autor;
        this.generoLibro = generoLibro;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
