package com.example.digitalBooking;

import com.example.digitalBooking.controller.UsuarioController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.UsuarioNotFoundException;
import com.example.digitalBooking.model.Usuario;
import com.example.digitalBooking.model.dto.UsuarioDTO;
import com.example.digitalBooking.service.UsuarioService;
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
public class UsuarioControllerTest {
    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private final UsuarioDTO usuarioDTO = new UsuarioDTO(1L,"nombre","apellido","email",
            "password","ciudad",1L);
    private final Usuario usuario = new Usuario();

    @Test
    @DisplayName("WHEN we create a usuario THEN return HTTP STATUS 201 CREATED and a message 'Se creo el usuario correctamente'")
    public void createUsuario() throws BadRequestException {
        //WHEN
        given(service.create(usuarioDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(usuarioDTO),new ResponseEntity<>("Se creo el usuario correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all usuario THEN return HTTP STATUS 200 OK and a list of usuario")
    public void getAllUsuario(){
        //WHEN
        given(service.getAll()).willReturn(List.of(usuario));
        //THEN
        assertEquals(controller.getAll(),ResponseEntity.ok(List.of(usuario)));
    }

    @Test
    @DisplayName("WHEN we bring a usuario by id THEN return HTTP STATUS 200 OK and a usuario")
    public void getUsuarioById() throws UsuarioNotFoundException {
        //WHEN
        given(service.getById(anyLong())).willReturn(usuario);
        //THEN
        assertEquals(controller.getById(anyLong()),ResponseEntity.ok(usuario));
    }

    @Test
    @DisplayName("WHEN we bring a caracteristica by titulo THEN return HTTP STATUS 200 OK and a usuario")
    public void getUsuarioByEmail() throws UsuarioNotFoundException {
        //WHEN
        given(service.getByEmail(anyString())).willReturn(usuario);
        //THEN
        assertEquals(controller.getByEmail(anyString()),ResponseEntity.ok(usuario));
    }

    @Test
    @DisplayName("WHEN we update a usuario THEN return HTTP STATUS 200 OK and a message 'Se edito el usuario correctamente'")
    public void updateUsuario() throws UsuarioNotFoundException {
        //WHEN
        given(service.update(usuarioDTO)).willReturn(true);
        //THEN
        assertEquals(controller.update(usuarioDTO),new ResponseEntity<>("Se edito el usuario correctamente", HttpStatus.OK));
    }

    @Test
    @DisplayName("WHEN we delete a usuario by id THEN return HTTP STATUS 200 OK and a message 'Se elimino el usuario'")
    public void deleteUsuarioById() throws UsuarioNotFoundException {
        //WHEN
        given(service.deleteById(anyLong())).willReturn(true);
        //THEN
        assertEquals(controller.deleteById(anyLong()),new ResponseEntity<>("Se elimino el usuario", HttpStatus.OK));
    }
}
