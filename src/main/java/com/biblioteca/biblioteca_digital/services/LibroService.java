package com.biblioteca.biblioteca_digital.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.entities.Libro;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.LibroNotFound;
import com.biblioteca.biblioteca_digital.repositories.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro findById(Long id) throws LibroNotFound {
        return libroRepository.findById(id).orElseThrow(() -> new LibroNotFound(id));
    }
}
