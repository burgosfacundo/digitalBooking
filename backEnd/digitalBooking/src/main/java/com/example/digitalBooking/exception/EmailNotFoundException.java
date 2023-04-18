package com.example.digitalBooking.exception;

public class EmailNotFoundException extends Exception{
    public EmailNotFoundException() {
        super("El email no se encuentra registrado.");
    }
}
