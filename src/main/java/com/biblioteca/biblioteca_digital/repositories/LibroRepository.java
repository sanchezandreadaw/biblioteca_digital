package com.biblioteca.biblioteca_digital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_digital.entities.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByUserId(Long userId);

    @Query("SELECT l FROM Libro l WHERE " +
            "LOWER(l.titulo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.autor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.generoLibro) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Libro> buscarLibros(@Param("keyword") String keyword);

}
