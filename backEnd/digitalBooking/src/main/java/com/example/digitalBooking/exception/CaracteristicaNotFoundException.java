package com.example.digitalBooking.exception;

public class CaracteristicaNotFoundException extends Exception{
    public CaracteristicaNotFoundException() {
        super("La caracteristica no fue encontrada en la base de datos.");
    }
}
