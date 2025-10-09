package com.biblioteca.biblioteca_digital.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.entities.Libro;
import com.biblioteca.biblioteca_digital.entities.User;
import com.biblioteca.biblioteca_digital.enums.GeneroLibro;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.DuplicateUser;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.InvalidMonth;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.LibroNotFound;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.UserNotFound;
import com.biblioteca.biblioteca_digital.repositories.LibroRepository;
import com.biblioteca.biblioteca_digital.repositories.UserRepository;
import com.biblioteca.biblioteca_digital.security.Encoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

    public User findById(Long id) throws UserNotFound {
        User usuario = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        return usuario;
    }

    public List<Libro> getLibrosUser(Long id) throws UserNotFound {
        User usuario = findById(id);

        if (usuario.getLibros() == null) {
            return new ArrayList<>();
        }
        return usuario.getLibros();
    }

    public void createUser(String nombre, String apellidos, String correo, String password, List<Libro> libros)
            throws DuplicateUser {

        if (!userFound(correo)) {

            if (libros == null) {
                libros = new ArrayList<>();
            }
            User usuario = User.builder()
                    .nombre(nombre.trim())
                    .apellidos(apellidos.trim())
                    .correo(correo.trim())
                    .password(Encoder.passwordEncoder().encode(password))
                    .libros(libros)
                    .build();
            userRepository.save(usuario);
        } else {
            throw new DuplicateUser(correo);
        }

    }

    public boolean userFound(String correo) {
        if (!userRepository.existsByCorreo(correo)) {
            return false;
        }
        return true;
    }

    public void guardarLectura(Long id_usuario, String titulo, String autor, GeneroLibro genero, LocalDate fechaInicio,
            LocalDate fechaFin) throws UserNotFound {

        User usuario = findById(id_usuario);
        Libro libro = Libro.builder()
                .titulo(titulo.trim())
                .autor(autor.trim())
                .generoLibro(genero)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .user(usuario)
                .build();

        libroRepository.save(libro);
    }

    public void updateLectura(Long id_libro, String titulo, String autor, GeneroLibro genero, LocalDate fechaInicio,
            LocalDate fechaFin) {
        Libro libro;
        try {
            libro = libroService.findById(id_libro);
            libro.setTitulo(titulo.trim());
            libro.setAutor(autor.trim());
            libro.setGeneroLibro(genero);
            libro.setFechaInicio(fechaInicio);
            libro.setFechaFin(fechaFin);

            libroRepository.save(libro);
        } catch (LibroNotFound e) {
            e.printStackTrace();
        }

    }

    public void eliminarLectura(Long id_libro) {
        try {
            Libro libro = libroService.findById(id_libro);
            libroRepository.delete(libro);
        } catch (LibroNotFound e) {
            e.printStackTrace();
        }

    }

    public int obtenerTotalLecturasMensuales(Long id_usuario) throws UserNotFound {
        User usuario = findById(id_usuario);
        int mesActual = LocalDate.now().getMonthValue();
        List<Libro> libros_usuario = new ArrayList<>();
        int contador = 0;

        if (usuario.getLibros() != null) {
            libros_usuario.addAll(usuario.getLibros());
            for (Libro libro : libros_usuario) {
                if (libro.getFechaFin().getMonthValue() == mesActual) {
                    contador += 1;
                }
            }
        }
        return contador;
    }

    public int obtenerTotalLecturasAnuales(Long id_usuario) throws UserNotFound {

        User usuario = findById(id_usuario);
        int anyoActual = LocalDate.now().getYear();
        List<Libro> libros_usuario = new ArrayList<>();
        int contador = 0;

        if (usuario.getLibros() != null) {
            libros_usuario.addAll(usuario.getLibros());
            for (Libro libro : libros_usuario) {
                if (libro.getFechaFin().getYear() == anyoActual) {
                    contador += 1;
                }
            }
        }
        return contador;
    }

    public List<Libro> getLecturasMensuales(Long id_usuario, int mes) throws InvalidMonth, UserNotFound {
        if (mes < 0 || mes > 12) {
            throw new InvalidMonth();
        }
        User usuario = findById(id_usuario);
        List<Libro> lecturas_usuario = new ArrayList<>();
        List<Libro> lecturas_mensuales = new ArrayList<>();

        if (usuario.getLibros() != null) {
            lecturas_usuario.addAll(usuario.getLibros());
            lecturas_mensuales = lecturas_usuario.stream().filter((libro) -> libro.getFechaFin().getMonthValue() == mes)
                    .toList();

        }

        return lecturas_mensuales;

    }

    public List<Libro> getLecturasAnuales(Long id_usuario, int year) throws UserNotFound, Exception {
        User usuario = findById(id_usuario);
        int anyoActual = LocalDate.now().getYear();
        if (year < 1999 || year > anyoActual) {
            throw new Exception("El año no puede ser inferior a 1999 ni superior al año actual");
        }

        List<Libro> lecturas_usuario = new ArrayList<>();
        List<Libro> lecturas_anuales = new ArrayList<>();

        if (usuario.getLibros() != null) {
            lecturas_usuario.addAll(usuario.getLibros());
            lecturas_anuales = lecturas_usuario.stream().filter((libro) -> libro.getFechaFin().getYear() == year)
                    .toList();

        }

        return lecturas_anuales;

    }
}
