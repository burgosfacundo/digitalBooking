package com.example.digitalBooking;


import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.Producto;
import com.example.digitalBooking.model.Usuario;
import com.example.digitalBooking.service.PuntuacionService;
import com.example.digitalBooking.model.dto.PuntuacionDTO;
import com.example.digitalBooking.repository.ProductoRepository;
import com.example.digitalBooking.repository.PuntuacionRepository;
import com.example.digitalBooking.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PuntuacionServiceTest {
    @Mock
    PuntuacionRepository repository;
    @Mock
    ProductoRepository productoRepository;
    @Mock
    UsuarioRepository usuarioRepository;
    @InjectMocks
    PuntuacionService service;
    private final Usuario usuario = new Usuario();
    private final Producto producto = new Producto();
    private PuntuacionDTO puntuacionDTO;

    @BeforeEach
    void setUp(){
        puntuacionDTO = new PuntuacionDTO(1L,10,1L,1L);
    }

    @Test
    @DisplayName("WHEN we create a puntuacion then don´t throws any exception")
    public void createPuntuacion(){
        //GIVEN
        given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(usuario));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.create(puntuacionDTO));
    }

    @Test
    @DisplayName("WHEN we create a puntuacion with idUsuario invalid then it throws BadRequestException")
    public void createPuntuacionException(){
        //GIVEN
        given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(puntuacionDTO));
    }

    @Test
    @DisplayName("WHEN we create a puntuacion with idProducto invalid then it throws BadRequestException")
    public void createPuntuacionException2(){
        //GIVEN
        given(productoRepository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(puntuacionDTO));
    }
    @Test
    @DisplayName("WHEN we list all the puntuaciones by idProducto THEN don´t throws any exception")
    public void getAllPuntuacionesByIdProducto(){
        //GIVEN
        given(productoRepository.findById(anyLong())).willReturn(Optional.of(producto));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getAllByIdProducto(anyLong()));
    }
    @Test
    @DisplayName("WHEN we list all the puntuaciones by idProducto THEN throws ProductoNotFoundException")
    public void getAllPuntuacionesByIdProductoNull() {
        //GIVEN
        given(productoRepository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(ProductoNotFoundException.class, () -> service.getAllByIdProducto(anyLong()));
    }

}
