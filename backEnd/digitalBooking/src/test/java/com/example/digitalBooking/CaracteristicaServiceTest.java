package com.example.digitalBooking;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CaracteristicaNotFoundException;
import com.example.digitalBooking.model.Caracteristica;
import com.example.digitalBooking.model.dto.CaracteristicaDTO;
import com.example.digitalBooking.repository.CaracteristicaRepository;
import com.example.digitalBooking.service.CaracteristicaService;
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
public class CaracteristicaServiceTest {
    @Mock
    private CaracteristicaRepository repository;
    @InjectMocks
    private CaracteristicaService service;
    private CaracteristicaDTO caracteristicaDTO;
    private final Caracteristica caracteristica = new Caracteristica();

    @BeforeEach
    void setUp(){caracteristicaDTO = new CaracteristicaDTO(1L, "wifi",
            "url");}

    @Test
    @DisplayName("WHEN we create a caracteristica then don´t throws any exception")
    public void createCaracteristica(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertDoesNotThrow(()->service.create(caracteristicaDTO));
    }
    @Test
    @DisplayName("WHEN we create a caracteristica with the repeated titulo then it throws BadRequestException")
    public void createCaracteristicaException(){
        //GIVEN

        given(repository.findByTitulo(anyString())).willReturn(Optional.of(caracteristica));
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(caracteristicaDTO));
    }

    @Test
    @DisplayName("WHEN we list all the caracteristica THEN don´t throws any exception")
    public void getAllCaracteristica(){
        //GIVEN
        given(repository.findAll()).willReturn(List.of(caracteristica));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getAll());
    }
    @Test
    @DisplayName("WHEN we list all the caracteristica THEN return null")
    public void getAllCaracteristicaNull(){
        //GIVEN
        given(repository.findAll()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAll());
    }

    @Test
    @DisplayName("WHEN we bring a caracteristica by id THEN don´t throws any exception")
    public void getByIdCaracteristica(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(caracteristica));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getById(1L));
    }
    @Test
    @DisplayName("WHEN we bring a caracteristica by id THEN it throws CaracteristicaNotFoundException")
    public void getByIdCaracteristicaException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CaracteristicaNotFoundException.class,()->service.getById(1L));
    }

    @Test
    @DisplayName("WHEN we bring a caracteristica by titulo THEN don´t throws any exception")
    public void getByTituloCaracteristica(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.of(caracteristica));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getByTitulo("wifi"));
    }
    @Test
    @DisplayName("WHEN we bring a caracteristica by titulo THEN it throws CaracteristicaNotFoundException")
    public void getByTituloCaracteristicaException(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CaracteristicaNotFoundException.class,()->service.getByTitulo("titulo"));
    }

    @Test
    @DisplayName("WHEN we update a caracteristica then don´t throws any exception")
    public void updateCaracteristica(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(caracteristica));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.update(caracteristicaDTO));
    }
    @Test
    @DisplayName("WHEN we update a caracteristica that not exists then it throws CaracteristicaNotFoundException")
    public void updateCaracteristicaNotFoundException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CaracteristicaNotFoundException.class,()->service.update(caracteristicaDTO));
    }

    @Test
    @DisplayName("WHEN we delete caracteristica THEN don´t throws any exception")
    public void deleteByIdCaracteristica(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(caracteristica));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.deleteById(1L));
    }
    @Test
    @DisplayName("WHEN we delete caracteristica that is not present in the db THEN it throws CaracteristicaNotFoundException")
    public void deleteByIdCaracteristicaException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CaracteristicaNotFoundException.class,()-> service.deleteById(5L));
    }
}
