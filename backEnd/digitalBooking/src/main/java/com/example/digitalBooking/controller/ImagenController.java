package com.example.digitalBooking.controller;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ImagenNotFoundException;
import com.example.digitalBooking.model.dto.ImagenDTO;
import com.example.digitalBooking.service.ImagenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/imagenes")
public class ImagenController {
    private final ImagenService service;

    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody ImagenDTO imagenDTO) throws BadRequestException{
        service.create(imagenDTO);
        return new ResponseEntity<>("Se creo la imagen correctamente", HttpStatus.CREATED);
    }
    @GetMapping("/todas")
    public ResponseEntity<List<ImagenDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<ImagenDTO> getById(@PathVariable Long id) throws ImagenNotFoundException {
        return  ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/titulo={titulo}")
    public ResponseEntity<ImagenDTO> getByTitulo(@PathVariable String titulo) throws ImagenNotFoundException {
        return  ResponseEntity.ok(service.getByTitulo(titulo));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> update(@RequestBody ImagenDTO imagenDTO) throws ImagenNotFoundException{
        service.update(imagenDTO);
        return new ResponseEntity<>("Se edito la imagen correctamente",HttpStatus.OK);
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id ) throws ImagenNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la imagen", HttpStatus.OK);
    }
}
