package com.biblioteca.biblioteca_digital.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_digital.enums.GeneroLibro;
import com.biblioteca.biblioteca_digital.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String getHome() {
        return "home/home";
    }

    @GetMapping("/lecturas_totales")
    public String getLecturasTotales() {
        return "lecturas/lecturas";
    }

    @PostMapping("/add")
    public void agregarLectura(@RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("genero") GeneroLibro genero,
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
    }

    @PutMapping("/update")
    public void actualizarLectura(@RequestParam("id") Long id, @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("genero") GeneroLibro genero,
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
    }

    @PostMapping("/delete")
    public void borrarLectura(@RequestParam("id") Long id) {
        try {
            userService.eliminarLectura(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
