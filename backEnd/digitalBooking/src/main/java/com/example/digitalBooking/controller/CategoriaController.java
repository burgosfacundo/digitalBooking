package com.example.digitalBooking.controller;



import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CategoriaNotFoundException;
import com.example.digitalBooking.model.dto.CategoriaDTO;
import com.example.digitalBooking.service.CategoriaService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService service;


    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody CategoriaDTO categoria) throws BadRequestException {
        service.create(categoria);
        return new ResponseEntity<>("Se creo la categoria correctamente", HttpStatus.CREATED);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<CategoriaDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) throws CategoriaNotFoundException {
        return  ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/titulo={titulo}")
    public ResponseEntity<CategoriaDTO> getByTitulo(@PathVariable String titulo) throws CategoriaNotFoundException {
        return  ResponseEntity.ok(service.getByTitulo(titulo));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> update(@RequestBody CategoriaDTO categoria) throws CategoriaNotFoundException{
        service.update(categoria);
        return new ResponseEntity<>("Se edito la categoria correctamente",HttpStatus.OK);
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id ) throws CategoriaNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la categoria", HttpStatus.OK);
    }

}
