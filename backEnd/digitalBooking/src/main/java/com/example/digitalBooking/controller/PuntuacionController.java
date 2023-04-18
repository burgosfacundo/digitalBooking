package com.example.digitalBooking.controller;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.dto.PuntuacionDTO;
import com.example.digitalBooking.service.PuntuacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/puntuaciones")
public class PuntuacionController {

    private final PuntuacionService service;


    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody PuntuacionDTO puntuacionDTO) throws BadRequestException {
        service.create(puntuacionDTO);
        return new ResponseEntity<>("Se creo la puntuacion correctamente", HttpStatus.CREATED);
    }

    @GetMapping("/idProducto={idProducto}")
    public ResponseEntity<List<PuntuacionDTO>> getByIdProducto(@PathVariable Long idProducto) throws ProductoNotFoundException {
        return ResponseEntity.ok(service.getAllByIdProducto(idProducto));
    }
}
