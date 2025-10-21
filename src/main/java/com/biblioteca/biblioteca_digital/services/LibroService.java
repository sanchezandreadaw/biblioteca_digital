package com.biblioteca.biblioteca_digital.services;

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
}
