package com.biblioteca.biblioteca_digital.exceptions;

public class Exceptions {

    public static class UserNotFound extends Exception {

        public UserNotFound(Long id) {
            super("El usuario con id " + id + " No existe");
        }
    }

    public static class LibroNotFound extends Exception {

        public LibroNotFound(Long id) {
            super("El libro con id " + id + " no existe");
        }
    }
}
