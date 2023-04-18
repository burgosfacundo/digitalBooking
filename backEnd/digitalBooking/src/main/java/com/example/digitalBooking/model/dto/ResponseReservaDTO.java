package com.example.digitalBooking.model.dto;

import com.example.digitalBooking.model.Imagen;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record ResponseReservaDTO(Long id, LocalTime horaComienzo,
                                 @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaInicial,
                                 @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaFinal, Long idUsuario,
                                 Long idProducto, String tituloProducto, Set<Imagen> imagenes){
}
