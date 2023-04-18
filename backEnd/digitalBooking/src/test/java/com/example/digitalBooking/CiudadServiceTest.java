package com.example.digitalBooking;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CiudadNotFoundException;
import com.example.digitalBooking.model.Ciudad;
import com.example.digitalBooking.model.dto.CiudadDTO;
import com.example.digitalBooking.repository.CiudadRepository;
import com.example.digitalBooking.service.CiudadService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CiudadServiceTest {
    @Mock
    private CiudadRepository repository;

    @InjectMocks
    private CiudadService service;
    private final Ciudad ciudad = new Ciudad();
    private CiudadDTO ciudadDTO;
    @BeforeEach
    void setUp(){  ciudadDTO = new CiudadDTO(1L,"Mar del Plata");}

    @Test
    @DisplayName("WHEN we create a ciudad then don´t throws any exception")
    public void createCiudad(){
        //GIVEN
        given(repository.findByNombre(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertDoesNotThrow(()->service.create(ciudadDTO));
    }
    @Test
    @DisplayName("WHEN we create a ciudad with the repeated name then it throws BadRequestException")
    public void createCiudadException(){
        //GIVEN

        given(repository.findByNombre(anyString())).willReturn(Optional.of(ciudad));
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(ciudadDTO));
    }

    @Test
    @DisplayName("WHEN we list all the ciudades THEN don´t throws any exception")
    public void getAllCiudades(){
        //GIVEN
        given(repository.findAll()).willReturn(List.of(ciudad));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getAll());
    }

    @Test
    @DisplayName("WHEN we list all the ciudades THEN return null")
    public void getAllCiudadesNull(){
        //GIVEN
        given(repository.findAll()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAll());
    }


    @Test
    @DisplayName("WHEN we bring a ciudad by id THEN don´t throws any exception")
    public void getByIdCiudad(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(ciudad));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getById(1L));
    }
    @Test
    @DisplayName("WHEN we bring a ciudad by id THEN it throws CiudadNotFoundException")
    public void getByIdCiudadException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CiudadNotFoundException.class,()->service.getById(1L));
    }

    @Test
    @DisplayName("WHEN we bring a ciudad by name THEN don´t throws any exception")
    public void getByNombreCiudad(){
        //GIVEN
        given(repository.findByNombre(anyString())).willReturn(Optional.of(ciudad));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getByNombre("Mar del Plata"));
    }
    @Test
    @DisplayName("WHEN we bring a ciudad by name THEN it throws CiudadNotFoundException")
    public void getByNombreCiudadException(){
        //GIVEN
        given(repository.findByNombre(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CiudadNotFoundException.class,()->service.getByNombre("nombre"));
    }

    @Test
    @DisplayName("WHEN we update a ciuidad then don´t throws any exception")
    public void updateCiudad(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(ciudad));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.update(ciudadDTO));
    }
    @Test
    @DisplayName("WHEN we update a ciudad that not exists then it throws CiudadNotFoundException")
    public void CiudadNotFoundException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CiudadNotFoundException.class,()->service.update(ciudadDTO));
    }

    @Test
    @DisplayName("WHEN we delete ciudad THEN don´t throws any exception")
    public void deleteByIdCiudad(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(ciudad));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.deleteById(1L));
    }
    @Test
    @DisplayName("WHEN we delete ciudad that is not present in the db THEN it throws CiudadNotFoundException")
    public void deleteByIdCiudadException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CiudadNotFoundException.class,()-> service.deleteById(5L));
    }
}
