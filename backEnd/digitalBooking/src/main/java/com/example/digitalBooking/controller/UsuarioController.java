package com.example.digitalBooking.controller;

import com.example.digitalBooking.config.jwt.JwtUtil;
import com.example.digitalBooking.config.jwt.model.AuthenticationRequest;
import com.example.digitalBooking.config.jwt.model.AuthenticationResponse;
import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.UsuarioNotFoundException;
import com.example.digitalBooking.model.Usuario;
import com.example.digitalBooking.model.dto.UsuarioDTO;
import com.example.digitalBooking.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody UsuarioDTO usuarioDTO) throws BadRequestException {
        service.create(usuarioDTO);
        return new ResponseEntity<>("Se creo el usuario correctamente", HttpStatus.CREATED);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) throws UsuarioNotFoundException {
        return  ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/email={email}")
    public ResponseEntity<Usuario> getByEmail(@PathVariable String email) throws UsuarioNotFoundException {
        return  ResponseEntity.ok(service.getByEmail(email));
    }

    @PutMapping("/editar")
    public ResponseEntity<String> update(@RequestBody UsuarioDTO usuarioDTO) throws UsuarioNotFoundException{
        service.update(usuarioDTO);
        return new ResponseEntity<>("Se edito el usuario correctamente",HttpStatus.OK);
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id ) throws UsuarioNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino el usuario", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e) {
            throw new Exception("Incorrect", e);
        }
        final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse((jwt)));
    }

}
