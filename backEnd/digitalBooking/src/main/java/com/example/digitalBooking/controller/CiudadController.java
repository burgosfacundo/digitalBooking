package com.example.digitalBooking.controller;


import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CiudadNotFoundException;
import com.example.digitalBooking.model.dto.CiudadDTO;
import com.example.digitalBooking.service.CiudadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    private final CiudadService service;

    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody CiudadDTO ciudadDTO) throws BadRequestException {
        service.create(ciudadDTO);
        return new ResponseEntity<>("Se creo la ciudad correctamente", HttpStatus.CREATED);
    }
    @GetMapping("/todas")
    public ResponseEntity<List<CiudadDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<CiudadDTO> getById(@PathVariable Long id) throws CiudadNotFoundException {
        return  ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/nombre={nombre}")
    public ResponseEntity<CiudadDTO> getByNombre(@PathVariable String nombre) throws CiudadNotFoundException {
        return  ResponseEntity.ok(service.getByNombre(nombre));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> update(@RequestBody CiudadDTO ciudadDTO) throws CiudadNotFoundException{
        service.update(ciudadDTO);
        return new ResponseEntity<>("Se edito la ciudad correctamente",HttpStatus.OK);
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id ) throws CiudadNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la ciudad", HttpStatus.OK);
    }
}
