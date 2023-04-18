package com.example.digitalBooking.exception;

public class ImagenNotFoundException extends Exception {
    public ImagenNotFoundException() {
        super("La imagen no fue encontrada en la base de datos.");
    }
}
