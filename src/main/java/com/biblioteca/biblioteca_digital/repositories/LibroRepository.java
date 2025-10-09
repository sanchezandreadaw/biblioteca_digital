package com.biblioteca.biblioteca_digital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_digital.entities.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

}
