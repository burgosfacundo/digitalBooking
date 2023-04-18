package com.example.digitalBooking.exception;

public class CategoriaNotFoundException extends Exception{
    public CategoriaNotFoundException() {
        super("La categoria no fue encontrada en la base de datos.");
    }
}
