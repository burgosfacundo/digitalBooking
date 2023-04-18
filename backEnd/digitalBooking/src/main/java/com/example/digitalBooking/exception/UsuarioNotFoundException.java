package com.example.digitalBooking.exception;

public class UsuarioNotFoundException extends Exception {

    public UsuarioNotFoundException() {
        super("El usuario no fue encontrado en la base de datos.");
    }
}
