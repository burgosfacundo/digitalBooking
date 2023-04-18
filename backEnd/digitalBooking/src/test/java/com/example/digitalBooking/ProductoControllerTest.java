package com.example.digitalBooking;

import com.example.digitalBooking.controller.ProductoController;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.dto.RequestProductoDTO;
import com.example.digitalBooking.model.dto.ResponseProductoDTO;
import com.example.digitalBooking.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {
    @Mock
    private ProductoService service;
    @InjectMocks
    private ProductoController controller;
    private final RequestProductoDTO requestProductoDTO = new RequestProductoDTO(1L,"titulo","url",
            "direccion",210F,210F,"normas","salud","cancelacion",
            1L,1L, Set.of(1L),Set.of());
    private final ResponseProductoDTO responseProductoDTO =
            new ResponseProductoDTO(1L,"titulo","url", "direccion",210F,210F,
            "normas","seguridad","cancelacion",
                    null,null,null,null,null);

    @Test
    @DisplayName("WHEN we create a producto THEN return the id of that producto in the database")
    public void createProducto() throws BadRequestException {
        //WHEN
        given(service.create(requestProductoDTO)).willReturn(anyLong());
        //THEN
        assertEquals(controller.create(requestProductoDTO),new ResponseEntity<>(service.create(requestProductoDTO), HttpStatus.CREATED));
    }

    @Test
    @DisplayName("WHEN we list all productos THEN return HTTP STATUS 200 OK and a list of productos")
    public void getAllProductos(){
        //WHEN
        given(service.getAll()).willReturn(List.of(responseProductoDTO));
        //THEN
        assertEquals(controller.getAll(),ResponseEntity.ok(List.of(responseProductoDTO)));
    }

    @Test
    @DisplayName("WHEN we list all productos by order rand THEN return HTTP STATUS 200 OK and a list of productos")
    public void getAllProductosRandom(){
        //WHEN
        given(service.getAllRand()).willReturn(List.of(responseProductoDTO));
        //THEN
        assertEquals(controller.getAllRand(),ResponseEntity.ok(List.of(responseProductoDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a producto by id THEN return HTTP STATUS 200 OK and a producto")
    public void getProductoById() throws ProductoNotFoundException {
        //WHEN
        given(service.getById(anyLong())).willReturn(responseProductoDTO);
        //THEN
        assertEquals(controller.getById(anyLong()),ResponseEntity.ok(responseProductoDTO));
    }

    @Test
    @DisplayName("WHEN we edit a producto THEN return HTTP Status 200 and message 'Se edito el producto correctamente'")
    public void editProducto() throws BadRequestException, ProductoNotFoundException {
        //WHEN
        given(service.update(requestProductoDTO)).willReturn(true);
        //THEN
        assertEquals(controller.putByid(requestProductoDTO),new ResponseEntity<>("Se edito el producto correctamente", HttpStatus.OK));
    }

    @Test
    @DisplayName("WHEN we bring a producto by ciudad THEN return HTTP STATUS 200 OK and a producto")
    public void getProductoByCiudad(){
        //WHEN
        given(service.filterCiudad(anyString())).willReturn(List.of(responseProductoDTO));
        //THEN
        assertEquals(controller.filterCiudad(anyString()),ResponseEntity.ok(List.of(responseProductoDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a producto by categoria THEN return HTTP STATUS 200 OK and a producto")
    public void getProductoByCategoria(){
        //WHEN
        given(service.filterCategoria(anyString())).willReturn(List.of(responseProductoDTO));
        //THEN
        assertEquals(controller.filterCategoria(anyString()),ResponseEntity.ok(List.of(responseProductoDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a producto by 2 dates THEN return HTTP STATUS 200 OK and a list of productos")
    public void filterFechas(){
        //WHEN
        given(service.filterFechas(LocalDate.now(),LocalDate.now())).willReturn(List.of(responseProductoDTO));
        //THEN
        assertEquals(controller.filterFechas(LocalDate.now(),LocalDate.now()),ResponseEntity.ok(List.of(responseProductoDTO)));
    }

    @Test
    @DisplayName("WHEN we bring a producto by 2 dates and one city THEN return HTTP STATUS 200 OK and a list of productos")
    public void filterFechasAndCity(){
        //WHEN
        given(service.filterCiudadAndFechas("mar del plata",LocalDate.now(),LocalDate.now()))
                .willReturn(List.of(responseProductoDTO));
        //THEN
        assertEquals(controller.filterCiudadFechas("mar del plata",LocalDate.now(),LocalDate.now()),
                ResponseEntity.ok(List.of(responseProductoDTO)));
    }

    @Test
    @DisplayName("WHEN we delete a producto by id THEN return HTTP STATUS 200 OK and a message 'Se elimino el producto'")
    public void deletePoliticaById() throws ProductoNotFoundException {
        //WHEN
        given(service.deleteById(anyLong())).willReturn(true);
        //THEN
        assertEquals(controller.deleteById(anyLong()),new ResponseEntity<>("Se elimino el producto", HttpStatus.OK));
    }
}
