package com.example.digitalBooking;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CategoriaNotFoundException;
import com.example.digitalBooking.model.Categoria;
import com.example.digitalBooking.model.dto.CategoriaDTO;
import com.example.digitalBooking.repository.CategoriaRepository;
import com.example.digitalBooking.service.CategoriaService;
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
public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;
    private CategoriaDTO categoriaDTO;
    private final Categoria categoria = new Categoria();

    @BeforeEach
    void setUp(){categoriaDTO = new CategoriaDTO(1L, "Departamentos",
            "Lindos departamentos", "url");}

    @Test
    @DisplayName("WHEN we create a categoria then don´t throws any exception")
    public void createCategoria(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertDoesNotThrow(()->service.create(categoriaDTO));
    }
    @Test
    @DisplayName("WHEN we create a categoria with the repeated titulo then it throws BadRequestException")
    public void createCategoriaException(){
        //GIVEN

        given(repository.findByTitulo(anyString())).willReturn(Optional.of(categoria));
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(categoriaDTO));
    }

    @Test
    @DisplayName("WHEN we list all the categorias THEN don´t throws any exception")
    public void getAllCategorias(){
        //GIVEN
        given(repository.findAll()).willReturn(List.of(categoria));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getAll());
    }
    @Test
    @DisplayName("WHEN we list all the categorias THEN return null")
    public void getAllCategoriasNull(){
        //GIVEN
        given(repository.findAll()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAll());
    }

    @Test
    @DisplayName("WHEN we bring a categoria by id THEN don´t throws any exception")
    public void getByIdCategoria(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(categoria));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getById(1L));
    }
    @Test
    @DisplayName("WHEN we bring a categoria by id THEN it throws CategoriaNotFoundException")
    public void getByIdCategoriaException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CategoriaNotFoundException.class,()->service.getById(1L));
    }

    @Test
    @DisplayName("WHEN we bring a categoria by titulo THEN don´t throws any exception")
    public void getByTituloCategoria(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.of(categoria));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getByTitulo("Departamentos"));
    }
    @Test
    @DisplayName("WHEN we bring a categoria by titulo THEN it throws CategoriaNotFoundException")
    public void getByTituloCategoriaException(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CategoriaNotFoundException.class,()->service.getByTitulo("titulo"));
    }

    @Test
    @DisplayName("WHEN we update a categoria then don´t throws any exception")
    public void updateCategoria(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(categoria));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.update(categoriaDTO));
    }
    @Test
    @DisplayName("WHEN we update a categoria that not exists then it throws CategoriaNotFoundException")
    public void updateCategoriaNotFoundException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CategoriaNotFoundException.class,()->service.update(categoriaDTO));
    }

    @Test
    @DisplayName("WHEN we delete categoria THEN don´t throws any exception")
    public void deleteByIdCategoria(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(categoria));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.deleteById(1L));
    }
    @Test
    @DisplayName("WHEN we delete categoria that is not present in the db THEN it throws CategoriaNotFoundException")
    public void deleteByIdCategoriaException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(CategoriaNotFoundException.class,()-> service.deleteById(5L));
    }
}
