package com.example.digitalBooking;

import com.example.digitalBooking.controller.CaracteristicaController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CaracteristicaNotFoundException;
import com.example.digitalBooking.model.dto.CaracteristicaDTO;
import com.example.digitalBooking.service.CaracteristicaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CaracteristicaControllerTest {
    @Mock
    private CaracteristicaService service;

    @InjectMocks
    private CaracteristicaController controller;

    private final CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO(1L,"titulo","url");

    @Test
    @DisplayName("WHEN we create a caracteristica THEN return HTTP STATUS 201 CREATED and a message 'Se creo la caracteristica correctamente'")
    public void createCaracteristica() throws BadRequestException {
        //WHEN
        given(service.create(caracteristicaDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(caracteristicaDTO),new ResponseEntity<>("Se creo la caracteristica correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all caracteristica THEN return HTTP STATUS 200 OK and a list of caracteristicas")
    public void getAllCaracteristica(){
        //WHEN
        given(service.getAll()).willReturn(List.of(caracteristicaDTO));
        //THEN
        assertEquals(controller.getAll(),ResponseEntity.ok(List.of(caracteristicaDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a caracteristica by id THEN return HTTP STATUS 200 OK and a caracteristica")
    public void getCaracteristicaById() throws CaracteristicaNotFoundException {
        //WHEN
        given(service.getById(anyLong())).willReturn(caracteristicaDTO);
        //THEN
        assertEquals(controller.getById(anyLong()),ResponseEntity.ok(caracteristicaDTO));
    }

    @Test
    @DisplayName("WHEN we bring a caracteristica by titulo THEN return HTTP STATUS 200 OK and a caracteristica")
    public void getCaracteristicaByTitulo() throws CaracteristicaNotFoundException {
        //WHEN
        given(service.getByTitulo(anyString())).willReturn(caracteristicaDTO);
        //THEN
        assertEquals(controller.getByTitulo(anyString()),ResponseEntity.ok(caracteristicaDTO));
    }

    @Test
    @DisplayName("WHEN we update a caracteristica THEN return HTTP STATUS 200 OK and a message 'Se edito la caracteristica correctamente'")
    public void updateCaracteristica() throws CaracteristicaNotFoundException {
        //WHEN
        given(service.update(caracteristicaDTO)).willReturn(true);
        //THEN
        assertEquals(controller.update(caracteristicaDTO),new ResponseEntity<>("Se edito la caracteristica correctamente", HttpStatus.OK));
    }

    @Test
    @DisplayName("WHEN we delete a caracteristica by id THEN return HTTP STATUS 200 OK and a message 'Se elimino la caracteristica'")
    public void deleteCaracteristicaById() throws CaracteristicaNotFoundException {
        //WHEN
        given(service.deleteById(anyLong())).willReturn(true);
        //THEN
        assertEquals(controller.deleteById(anyLong()),new ResponseEntity<>("Se elimino la caracteristica", HttpStatus.OK));
    }
}
