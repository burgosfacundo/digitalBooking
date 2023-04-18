package com.example.digitalBooking;

import com.example.digitalBooking.controller.CategoriaController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CategoriaNotFoundException;
import com.example.digitalBooking.model.dto.CategoriaDTO;
import com.example.digitalBooking.service.CategoriaService;
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
public class CategoriaControllerTest {
    @Mock
    private CategoriaService service;
    @InjectMocks
    private CategoriaController controller;
    private final CategoriaDTO categoriaDTO = new CategoriaDTO(1L,"titulo","descripcion","url");

    @Test
    @DisplayName("WHEN we create a categoria THEN return HTTP STATUS 201 CREATED and a message 'Se creo la categoria correctamente'")
    public void createCategoria() throws BadRequestException {
        //WHEN
        given(service.create(categoriaDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(categoriaDTO),new ResponseEntity<>("Se creo la categoria correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all categorias THEN return HTTP STATUS 200 OK and a list of categorias")
    public void getAllCategorias(){
        //WHEN
        given(service.getAll()).willReturn(List.of(categoriaDTO));
        //THEN
        assertEquals(controller.getAll(),ResponseEntity.ok(List.of(categoriaDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a categoria by id THEN return HTTP STATUS 200 OK and a categoria")
    public void getCategoriaById() throws CategoriaNotFoundException {
        //WHEN
        given(service.getById(anyLong())).willReturn(categoriaDTO);
        //THEN
        assertEquals(controller.getById(anyLong()),ResponseEntity.ok(categoriaDTO));
    }

    @Test
    @DisplayName("WHEN we bring a categoria by titulo THEN return HTTP STATUS 200 OK and a categoria")
    public void getCategoriaByTitulo() throws CategoriaNotFoundException {
        //WHEN
        given(service.getByTitulo(anyString())).willReturn(categoriaDTO);
        //THEN
        assertEquals(controller.getByTitulo(anyString()),ResponseEntity.ok(categoriaDTO));
    }

    @Test
    @DisplayName("WHEN we update a categoria THEN return HTTP STATUS 200 OK and a message 'Se edito la categoria correctamente'")
    public void updateCategoria() throws CategoriaNotFoundException {
        //WHEN
        given(service.update(categoriaDTO)).willReturn(true);
        //THEN
        assertEquals(controller.update(categoriaDTO),new ResponseEntity<>("Se edito la categoria correctamente", HttpStatus.OK));
    }

    @Test
    @DisplayName("WHEN we delete a categoria by id THEN return HTTP STATUS 200 OK and a message 'Se elimino la categoria'")
    public void deleteCategoriaById() throws CategoriaNotFoundException {
        //WHEN
        given(service.deleteById(anyLong())).willReturn(true);
        //THEN
        assertEquals(controller.deleteById(anyLong()),new ResponseEntity<>("Se elimino la categoria", HttpStatus.OK));
    }
}
