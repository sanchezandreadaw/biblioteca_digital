package com.biblioteca.biblioteca_digital.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.entities.Libro;
import com.biblioteca.biblioteca_digital.entities.User;
import com.biblioteca.biblioteca_digital.exceptions.Exceptions.UserNotFound;
import com.biblioteca.biblioteca_digital.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

}
