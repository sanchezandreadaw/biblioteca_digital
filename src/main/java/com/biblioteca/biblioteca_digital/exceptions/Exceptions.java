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

    public static class DuplicateUser extends Exception {

        public DuplicateUser(String correo) {
            super("El usuario con correo " + correo + " ya existe");
        }
    }

    public static class InvalidMonth extends Exception {
        public InvalidMonth() {
            super("El mes no puede ser menor que 1 o mayor que 12");
        }
    }
}
