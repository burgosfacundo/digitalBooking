package com.example.digitalBooking;

import com.example.digitalBooking.controller.CiudadController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CiudadNotFoundException;
import com.example.digitalBooking.model.dto.CiudadDTO;
import com.example.digitalBooking.service.CiudadService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CiudadControllerTest {
    @Mock
    private CiudadService service;
    @InjectMocks
    private CiudadController controller;
    private final CiudadDTO ciudadDTO = new CiudadDTO(1L,"nombre");

    @Test
    @DisplayName("WHEN we create a ciudad THEN return HTTP STATUS 201 CREATED and a message 'Se creo la ciudad correctamente'")
    public void createCiudad() throws BadRequestException {
        //WHEN
        given(service.create(ciudadDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(ciudadDTO),new ResponseEntity<>("Se creo la ciudad correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all ciudades THEN return HTTP STATUS 200 OK and a list of ciudades")
    public void getAllCiudad(){
        //WHEN
        given(service.getAll()).willReturn(List.of(ciudadDTO));
        //THEN
        assertEquals(controller.getAll(),ResponseEntity.ok(List.of(ciudadDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a ciudad by id THEN return HTTP STATUS 200 OK and a ciudad")
    public void getCiudadById() throws CiudadNotFoundException {
        //WHEN
        given(service.getById(anyLong())).willReturn(ciudadDTO);
        //THEN
        assertEquals(controller.getById(anyLong()),ResponseEntity.ok(ciudadDTO));
    }

    @Test
    @DisplayName("WHEN we bring a ciudad by nombre THEN return HTTP STATUS 200 OK and a ciudad")
    public void getCiudadByNombre() throws CiudadNotFoundException {
        //WHEN
        given(service.getByNombre(anyString())).willReturn(ciudadDTO);
        //THEN
        assertEquals(controller.getByNombre(anyString()),ResponseEntity.ok(ciudadDTO));
    }

    @Test
    @DisplayName("WHEN we update a ciudad THEN return HTTP STATUS 200 OK and a message 'Se edito la ciudad correctamente'")
    public void updateCiudad() throws CiudadNotFoundException {
        //WHEN
        given(service.update(ciudadDTO)).willReturn(true);
        //THEN
        assertEquals(controller.update(ciudadDTO),new ResponseEntity<>("Se edito la ciudad correctamente", HttpStatus.OK));
    }

    @Test
    @DisplayName("WHEN we delete a ciudad by id THEN return HTTP STATUS 200 OK and a message 'Se elimino la ciudad'")
    public void deleteCiudadById() throws CiudadNotFoundException {
        //WHEN
        given(service.deleteById(anyLong())).willReturn(true);
        //THEN
        assertEquals(controller.deleteById(anyLong()),new ResponseEntity<>("Se elimino la ciudad", HttpStatus.OK));
    }
}
