package com.biblioteca.biblioteca_digital.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres.")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(min = 3, message = "Los apellidos deben tener al menos 3 caracteres.")
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe proporcionar un correo electrónico válido.")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;

    @NotBlank(message = "La confirmación de clave es obligatoria")
    private String confirmPassword;

    public RegistroDTO() {
    }

    public RegistroDTO(String nombre, String apellidos, String correo, String password, String confirmPassword) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}