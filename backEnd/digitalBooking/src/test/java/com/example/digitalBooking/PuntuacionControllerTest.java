package com.example.digitalBooking;

import com.example.digitalBooking.controller.PuntuacionController;
import com.example.digitalBooking.service.PuntuacionService;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.dto.PuntuacionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class PuntuacionControllerTest {
    @Mock
    private PuntuacionService service;
    @InjectMocks
    private PuntuacionController controller;
    private final PuntuacionDTO puntuacionDTO = new PuntuacionDTO(1L, 10, 1L,1L);

    @Test
    @DisplayName("WHEN we create a puntuacion THEN return HTTP STATUS 201 CREATED and a message 'Se creo la puntuacion correctamente'")
    public void createPuntuacion() throws BadRequestException {
        //WHEN
        given(service.create(puntuacionDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(puntuacionDTO),new ResponseEntity<>("Se creo la puntuacion correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all puntuaciones by idProducto THEN return HTTP STATUS 200 OK and a list of puntuaciones")
    public void getAllPuntuacionesByIdProducto() throws ProductoNotFoundException {
        //WHEN
        given(service.getAllByIdProducto(anyLong())).willReturn(List.of(puntuacionDTO));
        //THEN
        assertEquals(controller.getByIdProducto(anyLong()),ResponseEntity.ok(List.of(puntuacionDTO)));
    }
}
