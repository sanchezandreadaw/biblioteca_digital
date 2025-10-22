package com.biblioteca.biblioteca_digital.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.entities.Libro;
import com.biblioteca.biblioteca_digital.entities.User;
import com.biblioteca.biblioteca_digital.enums.GeneroLibro;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.UserNotFound;
import com.biblioteca.biblioteca_digital.repositories.LibroRepository;
import com.biblioteca.biblioteca_digital.repositories.UserRepository;
import com.biblioteca.biblioteca_digital.security.PasswordConfig;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private PasswordConfig passwordConfig;

    public Optional<User> findById(Long id) {
        Optional<User> usuario = userRepository.findById(id);
        return usuario;
    }

    public List<Libro> getLibrosUser(Long id) throws UserNotFound {
        Optional<User> usuario = findById(id);

        if (usuario.get().getLibros() == null) {
            return new ArrayList<>();
        }

        return usuario.get().getLibros();
    }

    public void createUser(String nombre, String apellidos, String correo, String password, List<Libro> libros) {

        if (libros == null) {
            libros = new ArrayList<>();
        }
        User usuario = User.builder()
                .nombre(nombre.trim())
                .apellidos(apellidos.trim())
                .correo(correo.trim())
                .password(passwordConfig.passwordEncoder().encode(password))
                .libros(libros)
                .build();
        userRepository.save(usuario);

    }

    public User getByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    public User getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return null;
        }
        return getByCorreo(auth.getName());
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordConfig.passwordEncoder().matches(rawPassword, encodedPassword);
    }

    public boolean userFound(String correo) {
        if (!userRepository.existsByCorreo(correo)) {
            return false;
        }
        return true;
    }

    public void guardarLectura(Long id_usuario, String titulo, String autor, GeneroLibro genero, LocalDate fechaInicio,
            LocalDate fechaFin) {
        Optional<User> usuario = findById(id_usuario);
        Libro libro = Libro.builder()
                .titulo(titulo.trim())
                .autor(autor.trim())
                .generoLibro(genero)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .user(usuario.get())
                .build();

        libroRepository.save(libro);
    }

    public void updateLectura(Long id_libro, String titulo, String autor, GeneroLibro genero, LocalDate fechaInicio,
            LocalDate fechaFin) {
        Optional<Libro> libro;

        libro = libroService.findById(id_libro);
        libro.get().setTitulo(titulo.trim());
        libro.get().setAutor(autor.trim());
        libro.get().setGeneroLibro(genero);
        libro.get().setFechaInicio(fechaInicio);
        libro.get().setFechaFin(fechaFin);
        libroRepository.save(libro.get());

    }

    public void eliminarLectura(Long id_libro) {
        Optional<Libro> libro = libroService.findById(id_libro);
        libroRepository.delete(libro.get());
    }

    public int obtenerTotalLecturasMensuales(Long id_usuario) {
        Optional<User> usuario = findById(id_usuario);
        int mesActual = LocalDate.now().getMonthValue();
        List<Libro> libros_usuario = new ArrayList<>();
        int contador = 0;

        if (usuario.get().getLibros() != null) {
            libros_usuario.addAll(usuario.get().getLibros());
            for (Libro libro : libros_usuario) {
                if (libro.getFechaFin().getMonthValue() == mesActual) {
                    contador += 1;
                }
            }
        }
        return contador;
    }

    public int obtenerTotalLecturasAnuales(Long id_usuario) {

        Optional<User> usuario = findById(id_usuario);
        int anyoActual = LocalDate.now().getYear();
        List<Libro> libros_usuario = new ArrayList<>();
        int contador = 0;

        if (usuario.get().getLibros() != null) {
            libros_usuario.addAll(usuario.get().getLibros());
            for (Libro libro : libros_usuario) {
                if (libro.getFechaFin().getYear() == anyoActual) {
                    contador += 1;
                }
            }
        }
        return contador;
    }

    public List<Libro> getLecturasMensuales(Long id_usuario, int mes) {
        Optional<User> usuario = findById(id_usuario);
        List<Libro> lecturas_usuario = new ArrayList<>();
        List<Libro> lecturas_mensuales = new ArrayList<>();

        if (usuario.get().getLibros() != null) {
            lecturas_usuario.addAll(usuario.get().getLibros());
            lecturas_mensuales = lecturas_usuario.stream().filter((libro) -> libro.getFechaFin().getMonthValue() == mes)
                    .toList();

        }
        return lecturas_mensuales;

    }

    public List<Libro> getLecturasAnuales(Long id_usuario, int year) {
        Optional<User> usuario = findById(id_usuario);
        List<Libro> lecturas_usuario = new ArrayList<>();
        List<Libro> lecturas_anuales = new ArrayList<>();

        if (usuario.get().getLibros() != null) {
            lecturas_usuario.addAll(usuario.get().getLibros());
            lecturas_anuales = lecturas_usuario.stream().filter((libro) -> libro.getFechaFin().getYear() == year)
                    .toList();

        }

        return lecturas_anuales;

    }

    public List<Libro> getLecturasByMesAndByAnyo(Long idUsuario, int mes, int year) {
        Optional<User> usuario = findById(idUsuario);
        List<Libro> lecturas_usuario = new ArrayList<>();
        List<Libro> lecturas_x_mes_anyo = new ArrayList<>();

        if (usuario.get().getLibros() != null) {
            lecturas_usuario.addAll(usuario.get().getLibros());
            lecturas_x_mes_anyo = lecturas_usuario.stream().filter(
                    (libro) -> libro.getFechaFin().getMonthValue() == mes && libro.getFechaFin().getYear() == year)
                    .toList();
        }
        return lecturas_x_mes_anyo;
    }

    public List<Libro> getLecturasPorGenero(Long idUsuario, GeneroLibro generoLibro) {
        Optional<User> usuario = findById(idUsuario);
        List<Libro> lecturas_usuario = new ArrayList<>();
        List<Libro> lecturas_filtradas = new ArrayList<>();

        if (usuario.get().getLibros() != null) {
            lecturas_usuario.addAll(usuario.get().getLibros());
            lecturas_filtradas = lecturas_usuario.stream().filter((libro) -> libro.getGeneroLibro() == generoLibro)
                    .toList();
        }
        return lecturas_filtradas;
    }
}
