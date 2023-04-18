package com.example.digitalBooking.exception;

public class CiudadNotFoundException extends Exception{
    public CiudadNotFoundException() {
        super("La ciudad no fue encontrada en la base de datos.");
    }
}
