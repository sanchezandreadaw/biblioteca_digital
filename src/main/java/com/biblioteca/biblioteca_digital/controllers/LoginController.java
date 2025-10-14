package com.biblioteca.biblioteca_digital.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.biblioteca.biblioteca_digital.dtos.LoginDTO;
import com.biblioteca.biblioteca_digital.services.UserService;

import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        if (!model.containsAttribute("login")) {
            model.addAttribute("login", new LoginDTO());
        }
        return "login/login";
    }

    @PostMapping("/validar_credenciales")
    public String validarCredenciales(@Valid @ModelAttribute("login") LoginDTO loginDTO, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("login", loginDTO);
            return "login/login";
        }

        if (!userService.signIn(loginDTO.getCorreo(), loginDTO.getPassword())) {
            model.addAttribute("mensajeError", "Correo electrónico o contraseña incorrectos.");
            return "login/login";
        }

        return "redirect:/home";

    }

}
