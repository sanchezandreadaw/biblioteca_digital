package com.biblioteca.biblioteca_digital.helpers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticData {

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static char[] abecedario = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'
    };

    public static List<String> validateUser(String nombre, String apellidos, String correo, String password) {
        List<String> errores = new ArrayList<>();

        if (nombre.length() < 3) {
            errores.add("El nombre debe tener al menos 3 caracteres.");
        }

        if (apellidos.length() < 3) {
            errores.add("Los apellidos deben tener al menos 3 caracteres.");
        }

        if (!correo.contains("@")) {
            errores.add("El correo debe contener un dominio válido.");
        }
        errores.addAll(validatePassword(password));

        return errores;
    }

    public static List<String> validatePassword(String password) {
        List<String> errores = new ArrayList<>();

        if (password.length() < 8) {
            errores.add("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!containsLowercase(password)) {
            errores.add("La contraseña debe tener al menos una minúscula.");
        }
        if (!containsUpperCase(password)) {
            errores.add("La contraseña debe tener al menos una mayúscula.");
        }
        if (!containsSpecialChar(password)) {
            errores.add("La contraseña debe tener al menos un caracter especial.");
        }

        return errores;
    }

    public static boolean containsLowercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsUpperCase(String password) {
        char[] upperCharArray = Arrays.copyOf(abecedario, 27);
        for (int i = 0; i < upperCharArray.length; i++) {
            upperCharArray[i] = Character.toUpperCase(upperCharArray[i]);
        }
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsSpecialChar(String password) {
        String especiales = "!@#$%^&*()_+-=[]{}|;:'\",.<>/?\\";

        for (char c : password.toCharArray()) {
            if (especiales.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

}
