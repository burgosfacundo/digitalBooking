package com.example.digitalBooking;

import com.example.digitalBooking.controller.ReservaController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.exception.UsuarioNotFoundException;
import com.example.digitalBooking.model.Imagen;
import com.example.digitalBooking.model.dto.RequestReservaDTO;
import com.example.digitalBooking.model.dto.ResponseReservaDTO;
import com.example.digitalBooking.service.ReservaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReservaControllerTest {
    @Mock
    private ReservaService service;
    @InjectMocks
    private ReservaController controller;
    private final ResponseReservaDTO responseReservaDTO = new ResponseReservaDTO(1L, LocalTime.now(), LocalDate.now(),LocalDate.now(),
            1L,1L,"titulo", Set.of(new Imagen()));

    private final RequestReservaDTO requestReservaDTO = new RequestReservaDTO(1L, LocalTime.now(), LocalDate.now(),LocalDate.now(),
            1L,"burgosfacundo@hotmail.com");

    @Test
    @DisplayName("WHEN we create a reserva THEN return HTTP STATUS 201 CREATED and a message 'Se creo la reserva correctamente'")
    public void createReserva() throws BadRequestException, MessagingException {
        //WHEN
        given(service.create(requestReservaDTO)).willReturn(true);
        //THEN
        assertEquals(controller.create(requestReservaDTO),new ResponseEntity<>("Se creo la reserva correctamente", HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all reservas by idProducto THEN return HTTP STATUS 200 OK and a list of reservas")
    public void getAllReservasByIdProducto() throws ProductoNotFoundException {
        //WHEN
        given(service.getAllByIdProducto(anyLong())).willReturn(List.of(responseReservaDTO));
        //THEN
        assertEquals(controller.getByIdProducto(anyLong()),ResponseEntity.ok(List.of(responseReservaDTO)));
    }

    @Test
    @DisplayName("WHEN we list all reservas by IdUsuario THEN return HTTP STATUS 200 OK and a list of reservas")
    public void getAllReservasByIdUsuario() throws UsuarioNotFoundException {
        //WHEN
        given(service.findAllByIdUsuario(anyLong())).willReturn(List.of(responseReservaDTO));
        //THEN
        assertEquals(controller.findAllByIdUsuario(anyLong()),ResponseEntity.ok(List.of(responseReservaDTO)));
    }

}
