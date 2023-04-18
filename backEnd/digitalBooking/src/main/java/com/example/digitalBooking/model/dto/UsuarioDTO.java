package com.example.digitalBooking.model.dto;

public record UsuarioDTO (Long id, String nombre, String apellido, String email,
                          String password, String ciudad, Long idRol){
}
