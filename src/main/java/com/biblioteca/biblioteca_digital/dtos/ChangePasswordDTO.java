package com.biblioteca.biblioteca_digital.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    private Long idUsuario;

    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String correo;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String confirmPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String correo, String password, String confirmPassword) {
        this.correo = correo;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
