package com.biblioteca.biblioteca_digital.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.biblioteca.biblioteca_digital.dtos.RegistroDTO;
import com.biblioteca.biblioteca_digital.helpers.StaticData;
import com.biblioteca.biblioteca_digital.services.UserService;

import jakarta.validation.Valid;

@Controller
public class RegistroController {

    @Autowired
    private UserService userService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("registro")) {
            model.addAttribute("registro", new RegistroDTO());
        }
        return "registro/registro";
    }

    @PostMapping("/alta")
    public String completarRegistro(
            @Valid @ModelAttribute("registro") RegistroDTO registroDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registro", registroDTO);
            return "registro/registro";
        }

        if (userService.userFound(registroDTO.getCorreo())) {
            model.addAttribute("duplicate_user", "La dirección de correo electrónico ya ha sido registrada.");
            model.addAttribute("registro", registroDTO);
            return "registro/registro";
        }

        String password = registroDTO.getPassword();
        if (!StaticData.containsLowercase(password)) {
            model.addAttribute("invalid_password", "La contraseña debe contener al menos una letra minúscula.");
            model.addAttribute("registro", registroDTO);
            return "registro/registro";
        }

        if (!StaticData.containsUpperCase(password)) {
            model.addAttribute("invalid_password", "La contraseña debe contener al menos una letra mayúscula.");
            model.addAttribute("registro", registroDTO);
            return "registro/registro";
        }

        if (!StaticData.containsSpecialChar(password)) {
            model.addAttribute("invalid_password", "La contraseña debe contener al menos un carácter especial.");
            model.addAttribute("registro", registroDTO);
            return "registro/registro";
        }

        String confirmPassword = registroDTO.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error_confirm_password", "Las contraseñas no coinciden");
            model.addAttribute("registro", registroDTO);
            return "registro/registro";
        }

        userService.createUser(
                registroDTO.getNombre(),
                registroDTO.getApellidos(),
                registroDTO.getCorreo(),
                registroDTO.getPassword(),
                null);

        return "redirect:/registro_exitoso";
    }

    @GetMapping("registro_exitoso")
    public String getRegistroExitosoView() {
        return "registro/registro_completado";
    }
}
