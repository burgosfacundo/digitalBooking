package com.example.digitalBooking;

import com.example.digitalBooking.controller.ImagenController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ImagenNotFoundException;
import com.example.digitalBooking.model.dto.ImagenDTO;
import com.example.digitalBooking.service.ImagenService;
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
public class ImagenControllerTest {
    @Mock
    private ImagenService service;
    @InjectMocks
    private ImagenController controller;
    private final ImagenDTO imagenDTO = new ImagenDTO(1L,"titulo","url",1L);

    @Test
    @DisplayName("WHEN we create a imagen THEN return HTTP STATUS 201 CREATED and a message 'Se creo la imagen correctamente'")
    public void createImagen() throws BadRequestException {
        //WHEN
        given(service.create(imagenDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(imagenDTO),new ResponseEntity<>("Se creo la imagen correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all imagenes THEN return HTTP STATUS 200 OK and a list of imagenes")
    public void getAllImagen(){
        //WHEN
        given(service.getAll()).willReturn(List.of(imagenDTO));
        //THEN
        assertEquals(controller.getAll(),ResponseEntity.ok(List.of(imagenDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a imagen by id THEN return HTTP STATUS 200 OK and a imagen")
    public void getImagenById() throws ImagenNotFoundException {
        //WHEN
        given(service.getById(anyLong())).willReturn(imagenDTO);
        //THEN
        assertEquals(controller.getById(anyLong()),ResponseEntity.ok(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we bring a imagen by titulo THEN return HTTP STATUS 200 OK and a imagen")
    public void getImagenByTitulo() throws ImagenNotFoundException {
        //WHEN
        given(service.getByTitulo(anyString())).willReturn(imagenDTO);
        //THEN
        assertEquals(controller.getByTitulo(anyString()),ResponseEntity.ok(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we update a imagen THEN return HTTP STATUS 200 OK and a message 'Se edito la imagen correctamente'")
    public void updateImagen() throws ImagenNotFoundException {
        //WHEN
        given(service.update(imagenDTO)).willReturn(true);
        //THEN
        assertEquals(controller.update(imagenDTO),new ResponseEntity<>("Se edito la imagen correctamente", HttpStatus.OK));
    }

    @Test
    @DisplayName("WHEN we delete a imagen by id THEN return HTTP STATUS 200 OK and a message 'Se elimino la imagen'")
    public void deleteImagenById() throws ImagenNotFoundException {
        //WHEN
        given(service.deleteById(anyLong())).willReturn(true);
        //THEN
        assertEquals(controller.deleteById(anyLong()),new ResponseEntity<>("Se elimino la imagen", HttpStatus.OK));
    }
}
