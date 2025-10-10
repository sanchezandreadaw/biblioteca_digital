package com.biblioteca.biblioteca_digital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroController {

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro/registro";
    }

}
