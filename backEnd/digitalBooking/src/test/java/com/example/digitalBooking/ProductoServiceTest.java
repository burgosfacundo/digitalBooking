package com.example.digitalBooking;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.*;
import com.example.digitalBooking.model.dto.RequestProductoDTO;
import com.example.digitalBooking.repository.*;
import com.example.digitalBooking.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository repository;
    @Mock
    private CiudadRepository ciudadRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private CaracteristicaRepository caracteristicaRepository;

    @InjectMocks
    private ProductoService service;
    private final Producto producto = new Producto();
    private final Ciudad ciudad = new Ciudad();
    private final Categoria categoria = new Categoria();
    private final Caracteristica caracteristica = new Caracteristica();
    private RequestProductoDTO requestProductoDTO;

    @BeforeEach
    void setUp(){
        requestProductoDTO =
                new RequestProductoDTO(1L,"Departamento","descripcion","direccion",22F,
            12F, "normas","salud","cancelacion",1L,1L,Set.of(1L),Set.of());}



    @Test
    @DisplayName("WHEN we create a producto with the repeated titulo then it throws BadRequestException")
    public void createProductoException(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.of(producto));
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(requestProductoDTO));
    }

    @Test
    @DisplayName("WHEN we create a producto with idCiudad invalid then it throws BadRequestException")
    public void createProductoException2(){
        //GIVEN
        given(ciudadRepository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(requestProductoDTO));
    }

    @Test
    @DisplayName("WHEN we list all the productos THEN don´t throws any exception")
    public void getAllProductos(){
        //GIVEN
        var productos = repository.findAllWithImagenes();
        //WHEN AND THEN
        if (!productos.isEmpty())
            assertDoesNotThrow(()->service.getAll());
    }
    @Test
    @DisplayName("WHEN we list all the productos THEN return null")
    public void getAllProductosNull(){
        //GIVEN
        given(repository.findAllWithImagenes()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAll());
    }

    @Test
    @DisplayName("WHEN we list all the productos order by random THEN don´t throws any exception")
    public void getAllProductosRandom(){
        //GIVEN
        var productos = repository.findAllWithImagenesRand();
        //WHEN AND THEN
        if (!productos.isEmpty())
            assertDoesNotThrow(()->service.getAllRand());
    }

    @Test
    @DisplayName("WHEN we list all the productos order by random THEN return null")
    public void getAllProductosRandomNull(){
        //GIVEN
        given(repository.findAllWithImagenesRand()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAllRand());
    }

    @Test
    @DisplayName("WHEN we bring a producto by id THEN it throws ProductoNotFoundException")
    public void getByIdProductoException(){
        //GIVEN
        given(repository.findByIdWithImagenes(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ProductoNotFoundException.class,()->service.getById(anyLong()));
    }

    @Test
    @DisplayName("WHEN we bring a producto by titulo THEN don´t throws any exception")
    public void getByTituloProducto(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.of(producto));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getByTitulo("titulo"));
    }
    @Test
    @DisplayName("WHEN we bring a producto by titulo THEN it throws ProductoNotFoundException")
    public void getByTituloCategoriaException(){
        //GIVEN
        given(repository.findByTitulo(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ProductoNotFoundException.class,()->service.getByTitulo("no hay titulo"));
    }

    @Test
    @DisplayName("WHEN we bring productos filter by categoria THEN don´t throws any exception")
    public void filterByCategoria(){
        //GIVEN
        var productos = repository.filterCategoria(anyString());
        //WHEN AND THEN
        if (!productos.isEmpty())
            assertDoesNotThrow(()->service.filterCategoria(anyString()));
    }

    @Test
    @DisplayName("WHEN we bring productos filter by categoria THEN return null")
    public void filterByCategoriaNull(){
        //GIVEN
        given(repository.filterCategoria(anyString())).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.filterCategoria(anyString()));
    }

    @Test
    @DisplayName("WHEN we bring productos filter by ciudad THEN don´t throws any exception")
    public void filterByCiudad(){
        //GIVEN
        var productos = repository.filterCiudad(anyString());
        //WHEN AND THEN
        if (!productos.isEmpty())
            assertDoesNotThrow(()->service.filterCiudad(anyString()));
    }

    @Test
    @DisplayName("WHEN we bring productos filter by ciudad THEN return null")
    public void filterByCiudadNull(){
        //GIVEN
        given(repository.filterCiudad(anyString())).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.filterCiudad(anyString()));
    }

    @Test
    @DisplayName("WHEN we update a producto then don´t throws any exception")
    public void updateProducto(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(producto));
        given(ciudadRepository.findById(anyLong())).willReturn(Optional.of(ciudad));
        given(categoriaRepository.findById(anyLong())).willReturn(Optional.of(categoria));
        given(caracteristicaRepository.findById(anyLong())).willReturn(Optional.of(caracteristica));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.update(requestProductoDTO));
    }


    @Test
    @DisplayName("WHEN we update a producto that not exists then it throws ProductoNotFoundException")
    public void updateProductoNotFoundException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ProductoNotFoundException.class,()->service.update(requestProductoDTO));
    }

    @Test
    @DisplayName("WHEN we delete producto THEN don´t throws any exception")
    public void deleteByIdProducto(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(producto));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.deleteById(1L));
    }
    @Test
    @DisplayName("WHEN we delete producto that is not present in the db THEN it throws ProductoNotFoundException")
    public void deleteByIdProductoException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ProductoNotFoundException.class,()-> service.deleteById(5L));
    }
}
