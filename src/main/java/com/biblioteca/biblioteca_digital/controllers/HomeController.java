package com.biblioteca.biblioteca_digital.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.biblioteca_digital.dtos.AddLecturaDTO;
import com.biblioteca.biblioteca_digital.dtos.UpdateLecturaDTO;
import com.biblioteca.biblioteca_digital.entities.Libro;
import com.biblioteca.biblioteca_digital.entities.User;
import com.biblioteca.biblioteca_digital.enums.GeneroLibro;
import com.biblioteca.biblioteca_digital.helpers.StaticData;
import com.biblioteca.biblioteca_digital.services.LibroService;
import com.biblioteca.biblioteca_digital.services.UserService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private LibroService libroService;

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
        int anyo_actual = LocalDate.now().getYear();
        String mes_actual = StaticData.fortmatMonth();

        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("anyo_actual", anyo_actual);
        model.addAttribute("mes_actual", mes_actual);

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

    @GetMapping("/view_update_lecturas")
    public String getViewLecturas(@RequestParam("id") Long id, Model model) {
        if (id == null) {
            return "home/home";
        }

        Optional<Libro> libro = libroService.findById(id);
        if (libro.isPresent()) {
            model.addAttribute("lectura", libro.get());
        }

        List<GeneroLibro> generos = Arrays.asList(GeneroLibro.values());
        model.addAttribute("generos", generos);

        return "lecturas/update_lectura";
    }

    @PostMapping("/update")
    public String actualizarLectura(@Valid @ModelAttribute("lectura") UpdateLecturaDTO updateLecturaDTO,
            BindingResult bindingResult, Model model) {
        User usuario = userService.getUsuarioLogado();
        List<GeneroLibro> generos = Arrays.asList(GeneroLibro.values());

        if (usuario == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("lectura", updateLecturaDTO);
            model.addAttribute("generos", generos);
            return "lecturas/update_lectura";
        }
        if (updateLecturaDTO.getFechaFin().isBefore(updateLecturaDTO.getFechaInicio())) {
            model.addAttribute("invalid_date",
                    "La fecha de finalización del libro no puede ser anterior a la fecha inicial");
            model.addAttribute("lectura", updateLecturaDTO);
            model.addAttribute("generos", generos);
            return "lecturas/update_lectura";
        }
        userService.updateLectura(
                updateLecturaDTO.getId(),
                updateLecturaDTO.getTitulo(),
                updateLecturaDTO.getAutor(),
                updateLecturaDTO.getGeneroLibro(),
                updateLecturaDTO.getFechaInicio(),
                updateLecturaDTO.getFechaFin());

        return "redirect:/home";

    }

    @PostMapping("/delete")
    public String borrarLectura(@RequestParam("id") Long id) {
        if (id == null) {
            return "home/home";
        }
        userService.eliminarLectura(id);
        return "redirect:/home";
    }
}
