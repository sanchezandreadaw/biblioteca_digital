package com.biblioteca.biblioteca_digital.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_digital.services.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login/login";
    }

    @PostMapping("/validar_credenciales")
    public String validarCredenciales(@RequestParam("correo") String correo, Model model,
            @RequestParam("password") String password) {

        if (userService.validate_login(correo, password)) {
            return "redirect:/home";
        } else {
            model.addAttribute("validador", false);
            model.addAttribute("mensajeError", "Correo o contraseña incorrectos.");
            return "login/login";
        }
    }

}
