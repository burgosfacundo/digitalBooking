package com.example.digitalBooking;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ImagenNotFoundException;
import com.example.digitalBooking.model.Imagen;
import com.example.digitalBooking.model.Producto;
import com.example.digitalBooking.model.dto.ImagenDTO;
import com.example.digitalBooking.repository.ImagenRepository;
import com.example.digitalBooking.repository.ProductoRepository;
import com.example.digitalBooking.service.ImagenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ImagenServiceTest {
    @Mock
    private ImagenRepository repository;
    @Mock
    private ProductoRepository productoRepository;
    @InjectMocks
    private ImagenService service;
    private ImagenDTO imagenDTO;
    private final Producto producto = new Producto();
    private final Imagen imagen = new Imagen(1L,"titulo","url",producto);


    @BeforeEach
    void setUp() {
        imagenDTO = new ImagenDTO(1L, "imagen",
                "url", 1L);
    }

    @Test
    @DisplayName("WHEN we create a imagen then don´t throws any exception")
    public void createImagen() {
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));
        //WHEN AND THEN
        assertDoesNotThrow(() -> service.create(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we create a imagen with the repeated titulo then it throws BadRequestException")
    public void createImagenException() {
        //GIVEN

        given(repository.findByTitulo(anyString())).willReturn(Optional.of(imagen));
        //WHEN AND THEN
        assertThrows(BadRequestException.class, () -> service.create(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we create a imagen with idProduct invalid then it throws BadRequestException")
    public void createImagenException2() {
        //GIVEN
        given(productoRepository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(BadRequestException.class, () -> service.create(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we list all the imagen THEN don´t throws any exception")
    public void getAllImagen(){
        //GIVEN
        given(repository.findAll()).willReturn(List.of(imagen));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getAll());
    }

    @Test
    @DisplayName("WHEN we list all the imagen THEN return null")
    public void getAllImagenNull() {
        //GIVEN
        given(repository.findAll()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAll());
    }

    @Test
    @DisplayName("WHEN we bring a imagen by id THEN don´t throws any exception")
    public void getByIdImagen() {
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(imagen));
        //WHEN AND THEN
        assertDoesNotThrow(() -> service.getById(1L));
    }

    @Test
    @DisplayName("WHEN we bring a imagen by id THEN it throws ImagenNotFoundException")
    public void getByIdImagenException() {
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ImagenNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    @DisplayName("WHEN we bring a imagen by titulo THEN don´t throws any exception")
    public void getByTituloImagen() {
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.of(imagen));
        //WHEN AND THEN
        assertDoesNotThrow(() -> service.getByTitulo("imagen"));
    }

    @Test
    @DisplayName("WHEN we bring a imagen by titulo THEN it throws ImagenNotFoundException")
    public void getByTituloImagenException() {
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ImagenNotFoundException.class, () -> service.getByTitulo("imagen"));
    }

    @Test
    @DisplayName("WHEN we update a imagen then don´t throws any exception")
    public void updateImagen() {
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(imagen));
        //WHEN AND THEN
        assertDoesNotThrow(() -> service.update(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we update a imagen that not exists then it throws ImagenNotFoundException")
    public void updateImagenNotFoundException() {
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ImagenNotFoundException.class, () -> service.update(imagenDTO));
    }

    @Test
    @DisplayName("WHEN we delete imagen THEN don´t throws any exception")
    public void deleteByIdImagen() {
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(imagen));
        //WHEN AND THEN
        assertDoesNotThrow(() -> service.deleteById(1L));
    }

    @Test
    @DisplayName("WHEN we delete imagen that is not present in the db THEN it throws ImagenNotFoundException")
    public void deleteByIdImagenException() {
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ImagenNotFoundException.class, () -> service.deleteById(5L));
    }
}