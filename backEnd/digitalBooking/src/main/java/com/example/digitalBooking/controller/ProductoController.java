package com.example.digitalBooking.controller;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.dto.RequestProductoDTO;
import com.example.digitalBooking.model.dto.ResponseProductoDTO;
import com.example.digitalBooking.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    @PostMapping("/crear")
    public ResponseEntity<Long> create(@RequestBody RequestProductoDTO productoDTO) throws BadRequestException {
        return new ResponseEntity<>(service.create(productoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<ResponseProductoDTO>> getAll(){return ResponseEntity.ok(service.getAll());}

    @GetMapping("/todasRandom")
    public ResponseEntity<List<ResponseProductoDTO>> getAllRand(){return ResponseEntity.ok(service.getAllRand());}

    @GetMapping("/id={id}")
    public ResponseEntity<ResponseProductoDTO> getById(@PathVariable Long id) throws ProductoNotFoundException {
        return  ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> putByid(@RequestBody RequestProductoDTO productoDTO) throws ProductoNotFoundException, BadRequestException {
       service.update(productoDTO);
        return new ResponseEntity<>("Se edito el producto correctamente", HttpStatus.OK);
    }
    @GetMapping("/filterCat={categoria}")
    public ResponseEntity<List<ResponseProductoDTO>> filterCategoria(@PathVariable String categoria)  {
        return ResponseEntity.ok(service.filterCategoria(categoria));
    }

    @GetMapping("/filterCity={ciudad}")
    public ResponseEntity<List<ResponseProductoDTO>> filterCiudad(@PathVariable String ciudad)  {
        return  ResponseEntity.ok(service.filterCiudad(ciudad));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ResponseProductoDTO>> filterFechas(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy")  LocalDate fechaInicio,
                                                                  @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaFin
    ) {
        return  ResponseEntity.ok(service.filterFechas(fechaInicio,fechaFin));
    }

    @GetMapping("/filterCityFechas={ciudad}")// /filterCityFechas=Mendoza?fechaInicio=01/01/2022&fechaFin=31/01/2022 --->llamada ejemplo
    public ResponseEntity<List<ResponseProductoDTO>> filterCiudadFechas(
            @PathVariable String ciudad,
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "dd/MM/yyyy")  LocalDate fechaInicio,
            @RequestParam("fechaFin")@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaFin
    ) {
        return ResponseEntity.ok(service.filterCiudadAndFechas(ciudad, fechaInicio, fechaFin));
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id ) throws ProductoNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino el producto", HttpStatus.OK);
    }
}
