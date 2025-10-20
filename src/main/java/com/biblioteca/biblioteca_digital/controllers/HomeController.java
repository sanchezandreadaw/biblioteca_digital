package com.biblioteca.biblioteca_digital.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_digital.dtos.AddLecturaDTO;
import com.biblioteca.biblioteca_digital.entities.User;
import com.biblioteca.biblioteca_digital.enums.GeneroLibro;
import com.biblioteca.biblioteca_digital.services.UserService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String getHome(Model model) {
        User usuario = userService.getUsuarioLogado();
        if (usuario == null) {
            return "redirect:/login";
        }
        if (usuario.getLibros() != null) {
            model.addAttribute("libros", usuario.getLibros());
        }
        return "home/home";
    }

    @GetMapping("/view_lecturas_totales")
    public String getLecturasTotales(Model model) {
        User usuario = userService.getUsuarioLogado();
        if (usuario == null) {
            return "redirect:/login";
        }
        int libros_totales_leidos = usuario.getLibros().size();
        model.addAttribute("total_leidos", libros_totales_leidos);

        int total_libros_por_mes = userService.obtenerTotalLecturasMensuales(usuario.getId());
        model.addAttribute("total_leidos_mes", total_libros_por_mes);

        int total_libros_por_anyo = userService.obtenerTotalLecturasAnuales(usuario.getId());
        model.addAttribute("total_leidos_anyo", total_libros_por_anyo);

        return "lecturas/lecturas_totales";
    }

    @GetMapping("/consultar_lecturas_mes")
    public String consultarLecturasPorMes() {
        return "lecturas/consultar_por_mes";
    }

    @GetMapping("/consultar_lecturas_anyo")
    public String consultarLecturasPorAnyo() {
        return "lecturas/consultar_por_anyo";
    }

    @GetMapping("/consultar_lecturas_genero")
    public String consultarLecturasPorGenero(Model model) {
        List<GeneroLibro> generos = Arrays.asList(GeneroLibro.values());
        model.addAttribute("generos", generos);
        return "lecturas/consultar_por_genero";
    }

    @GetMapping("/view_add_lectura")
    public String getFormToAdd(Model model) {
        if (!model.containsAttribute("lectura")) {
            model.addAttribute("lectura", new AddLecturaDTO());
        }
        model.addAttribute("generos", Arrays.asList(GeneroLibro.values()));
        return "lecturas/add_lectura";
    }

    @PostMapping("/add")
    public String agregarLectura(@Valid @ModelAttribute("lectura") AddLecturaDTO addLecturaDTO,
            BindingResult bindingResult, Model model) {

        User usuario = userService.getUsuarioLogado();
        if (usuario == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("lectura", addLecturaDTO);
            model.addAttribute("generos", Arrays.asList(GeneroLibro.values()));
            return "lecturas/add_lectura";
        }

        if (addLecturaDTO.getFechaFin().isBefore(addLecturaDTO.getFechaInicio())) {
            model.addAttribute("invalid_date",
                    "La fecha de finalización del libro no puede ser anterior a la fecha inicial");
            model.addAttribute("lectura", addLecturaDTO);
            model.addAttribute("generos", Arrays.asList(GeneroLibro.values()));
            return "lecturas/add_lectura";
        }

        userService.guardarLectura(
                usuario.getId(),
                addLecturaDTO.getTitulo(),
                addLecturaDTO.getAutor(),
                addLecturaDTO.getGeneroLibro(),
                addLecturaDTO.getFechaInicio(),
                addLecturaDTO.getFechaFin());

        return "redirect:/home";
    }

    @PutMapping("/update")
    public void actualizarLectura(@RequestParam("id") Long id,
            @RequestParam("titulo") String titulo,
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
