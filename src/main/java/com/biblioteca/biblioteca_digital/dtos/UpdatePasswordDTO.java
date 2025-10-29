package com.biblioteca.biblioteca_digital.dtos;

import jakarta.validation.constraints.Size;
import lombok.NonNull;

public class UpdatePasswordDTO {

    @NonNull()
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NonNull()
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String confirmPassword;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
