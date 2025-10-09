package com.biblioteca.biblioteca_digital.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.repositories.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
}
