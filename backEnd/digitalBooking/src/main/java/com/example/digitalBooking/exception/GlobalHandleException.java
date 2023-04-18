package com.example.digitalBooking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalHandleException {


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> badRequest(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /*Categoria*/
    @ExceptionHandler({CategoriaNotFoundException.class})
    public ResponseEntity<String> categoriaNotFound(CategoriaNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    /*Producto*/
    @ExceptionHandler({ProductoNotFoundException.class})
    public ResponseEntity<String> ProductoNotFoundException(ProductoNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /*Ciudad*/
    @ExceptionHandler({CiudadNotFoundException.class})
    public ResponseEntity<String> CiudadNotFoundException(CiudadNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    /*Imagen*/
    @ExceptionHandler({ImagenNotFoundException.class})
    public ResponseEntity<String> ImagenNotFoundException(ImagenNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    /*Caracteristica*/
    @ExceptionHandler({CaracteristicaNotFoundException.class})
    public ResponseEntity<String> CaracteristicaNotFoundException(CaracteristicaNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /*Usuario*/
    @ExceptionHandler({UsuarioNotFoundException.class})
    public ResponseEntity<String> UsernameNotFoundException(UsuarioNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    /*Login*/
    @ExceptionHandler({EmailNotFoundException.class})
    public ResponseEntity<String> EmailNotFoundException(EmailNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
