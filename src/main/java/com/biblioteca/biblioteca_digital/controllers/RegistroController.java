package com.biblioteca.biblioteca_digital.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_digital.exceptions.Exceptions.DuplicateUser;
import com.biblioteca.biblioteca_digital.services.UserService;

@Controller
public class RegistroController {

    @Autowired
    private UserService userService;

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro/registro";
    }

    @PostMapping("/alta")
    public String completarRegistro(@RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("correo") String correo,
            @RequestParam("password") String password,
            Model model) throws DuplicateUser {

        try {
            userService.createUser(nombre, apellidos, correo, password, null);
            return "redirect:login";
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "registro/registro";
        }

    }
}