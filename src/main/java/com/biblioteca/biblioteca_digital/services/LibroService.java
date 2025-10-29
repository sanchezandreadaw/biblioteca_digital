package com.biblioteca.biblioteca_digital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.entities.Libro;
import com.biblioteca.biblioteca_digital.repositories.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Optional<Libro> findById(Long id) {
        Optional<Libro> libro = libroRepository.findById(id);
        return libro;
    }

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public List<Libro> buscarLibros(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listarTodos();
        }
        return libroRepository.buscarLibros(keyword);
    }

    public List<Libro> buscarLibrosDeUsuario(Long userId, String keyword) {
        List<Libro> librosUsuario = libroRepository.findByUserId(userId);
        if (keyword == null || keyword.trim().isEmpty()) {
            return librosUsuario;
        }

        String lowerKeyword = keyword.toLowerCase();
        return librosUsuario.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(lowerKeyword)
                        || l.getAutor().toLowerCase().contains(lowerKeyword)
                        || l.getGeneroLibro().toString().toLowerCase().contains(lowerKeyword))
                .toList();
    }

}
