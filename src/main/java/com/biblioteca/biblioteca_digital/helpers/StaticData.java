package com.biblioteca.biblioteca_digital.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class StaticData {

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static char[] abecedario = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'
    };

    public static boolean isValidNameAndSurname(String nombre, String apellidos) {
        if (nombre.length() < 3 || apellidos.length() < 3) {
            return false;
        }
        return true;
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

    public static String fortmatMonth() {
        int mes_actual = LocalDate.now().getMonthValue();
        String mes = "";
        switch (mes_actual) {
            case 1:
                mes = "enero";
                break;
            case 2:
                mes = "febrero";
                break;
            case 3:
                mes = "marzo";
                break;
            case 4:
                mes = "abril";
                break;
            case 5:
                mes = "mayo";
                break;
            case 6:
                mes = "junio";
                break;
            case 7:
                mes = "julio";
                break;
            case 8:
                mes = "agosto";
                break;
            case 9:
                mes = "septiembre";
                break;
            case 10:
                mes = "octubre";
                break;
            case 11:
                mes = "noviembre";
                break;
            case 12:
                mes = "diciembre";
                break;
            default:
                mes = "Desconocido";
                break;
        }
        return mes;
    }

    public static String formatMonth(int mes) {
        String mes_string = "";
        switch (mes) {
            case 1:
                mes_string = "enero";
                break;
            case 2:
                mes_string = "febrero";
                break;
            case 3:
                mes_string = "marzo";
                break;
            case 4:
                mes_string = "abril";
                break;
            case 5:
                mes_string = "mayo";
                break;
            case 6:
                mes_string = "junio";
                break;
            case 7:
                mes_string = "julio";
                break;
            case 8:
                mes_string = "agosto";
                break;
            case 9:
                mes_string = "septiembre";
                break;
            case 10:
                mes_string = "octubre";
                break;
            case 11:
                mes_string = "noviembre";
                break;
            case 12:
                mes_string = "diciembre";
                break;
            default:
                mes_string = "Desconocido";
                break;
        }
        return mes_string;
    }

}
