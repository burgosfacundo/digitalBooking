package com.example.digitalBooking;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.UsuarioNotFoundException;
import com.example.digitalBooking.model.Rol;
import com.example.digitalBooking.model.Usuario;
import com.example.digitalBooking.model.dto.UsuarioDTO;
import com.example.digitalBooking.repository.RolRepository;
import com.example.digitalBooking.repository.UsuarioRepository;
import com.example.digitalBooking.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;
    private final Rol rol = new Rol();
    private final Usuario usuario = new Usuario();
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp(){
        usuarioDTO = new UsuarioDTO(1L,"nombre","apellido","email","password",
                "ciudad",1L);
    }
    @Test
    @DisplayName("WHEN we create a usuario then don´t throws any exception")
    public void createUsuario(){
        //GIVEN
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(rolRepository.findById(anyLong())).willReturn(Optional.of(rol));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.create(usuarioDTO));
    }

    @Test
    @DisplayName("WHEN we create a usuario with the repeated titulo then it throws BadRequestException")
    public void createUsuarioException(){
        //GIVEN
        given(repository.findByEmail(anyString())).willReturn(Optional.of(usuario));
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(usuarioDTO));
    }

    @Test
    @DisplayName("WHEN we create a usuario with idRol invalid then it throws BadRequestException")
    public void createUsuarioException2(){
        //GIVEN
        given(rolRepository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(BadRequestException.class,()->service.create(usuarioDTO));
    }

    @Test
    @DisplayName("WHEN we list all the usuarios THEN don´t throws any exception")
    public void getAllUsuarios(){
        //GIVEN
        var usuarios = repository.findAll();
        //WHEN AND THEN
        if (!usuarios.isEmpty())
            assertDoesNotThrow(()->service.getAll());
    }
    @Test
    @DisplayName("WHEN we list all the usuarios THEN return null")
    public void getAllUsuarioNull(){
        //GIVEN
        given(repository.findAll()).willReturn(Collections.emptyList());
        //WHEN AND THEN
        assertNull(service.getAll());
    }

    @Test
    @DisplayName("WHEN we list all the usuarios THEN return a list of usuarios")
    public void getAllUsuarios1(){
        //GIVEN
        var usuarios = repository.findAll();
        //WHEN AND THEN
        if (!usuarios.isEmpty())
            assertEquals(service.getAll(), repository.findAll());
    }

    @Test
    @DisplayName("WHEN we bring a usuario by id THEN don´t throws any exception")
    public void getByIdUsuario(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(usuario));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getById(1L));
    }

    @Test
    @DisplayName("WHEN we bring a usuario by id THEN it throws UsuarioNotFoundException")
    public void getByIdUsuarioException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(UsuarioNotFoundException.class,()->service.getById(anyLong()));
    }

    @Test
    @DisplayName("WHEN we bring a usuario by email THEN don´t throws any exception")
    public void getByEmailUsuario(){
        //GIVEN
        given(repository.findByEmail(anyString())).willReturn(Optional.of(usuario));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.getByEmail("email"));
    }
    @Test
    @DisplayName("WHEN we bring a usuario by email THEN it throws UsuarioNotFoundException")
    public void getByEmailUsuarioException(){
        //GIVEN
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(UsuarioNotFoundException.class,()->service.getByEmail("no hay email"));
    }

    @Test
    @DisplayName("WHEN we update a usuario then don´t throws any exception")
    public void updateUsuario(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(usuario));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.update(usuarioDTO));
    }


    @Test
    @DisplayName("WHEN we update a usuario that not exists then it throws UsuarioNotFoundException")
    public void updateProductoNotFoundException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(UsuarioNotFoundException.class,()->service.update(usuarioDTO));
    }

    @Test
    @DisplayName("WHEN we delete usuario THEN don´t throws any exception")
    public void deleteByIdUsuario(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.of(usuario));
        //WHEN AND THEN
        assertDoesNotThrow(()->service.deleteById(1L));
    }
    @Test
    @DisplayName("WHEN we delete usuario that is not present in the db THEN it throws UsuarioNotFoundException")
    public void deleteByIdUsuarioException(){
        //GIVEN
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        //WHEN AND THEN
        assertThrows(UsuarioNotFoundException.class,()-> service.deleteById(5L));
    }
}
