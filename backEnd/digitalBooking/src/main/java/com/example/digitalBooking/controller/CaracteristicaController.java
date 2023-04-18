package com.example.digitalBooking.controller;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CaracteristicaNotFoundException;
import com.example.digitalBooking.model.dto.CaracteristicaDTO;
import com.example.digitalBooking.service.CaracteristicaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/caracteristicas")
public class CaracteristicaController {
    private final CaracteristicaService service;


    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody CaracteristicaDTO caracteristicaDTO) throws BadRequestException {
        service.create(caracteristicaDTO);
        return new ResponseEntity<>("Se creo la caracteristica correctamente", HttpStatus.CREATED);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<CaracteristicaDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<CaracteristicaDTO> getById(@PathVariable Long id) throws CaracteristicaNotFoundException {
        return  ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/titulo={titulo}")
    public ResponseEntity<CaracteristicaDTO> getByTitulo(@PathVariable String titulo) throws CaracteristicaNotFoundException {
        return  ResponseEntity.ok(service.getByTitulo(titulo));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> update(@RequestBody CaracteristicaDTO caracteristicaDTO) throws CaracteristicaNotFoundException{
        service.update(caracteristicaDTO);
        return new ResponseEntity<>("Se edito la caracteristica correctamente",HttpStatus.OK);
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id ) throws CaracteristicaNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la caracteristica", HttpStatus.OK);
    }
}
