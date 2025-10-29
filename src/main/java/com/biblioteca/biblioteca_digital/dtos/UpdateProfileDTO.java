package com.biblioteca.biblioteca_digital.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateProfileDTO {

    private Long idUsuario;

    @Size(min = 3, message = "El nombre debe de tener al menos 3 caracteres")
    private String nombre;
    @Size(min = 3, message = "Los apellidos deben de tener al menos 3 caracteres")
    private String apellidos;
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String correo;

    public UpdateProfileDTO() {
    }

    public UpdateProfileDTO(String nombre, String apellidos, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;

    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
