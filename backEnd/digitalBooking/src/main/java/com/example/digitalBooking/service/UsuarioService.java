package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.EmailNotFoundException;
import com.example.digitalBooking.exception.UsuarioNotFoundException;
import com.example.digitalBooking.model.Rol;
import com.example.digitalBooking.model.Usuario;
import com.example.digitalBooking.model.dto.UsuarioDTO;
import com.example.digitalBooking.repository.RolRepository;
import com.example.digitalBooking.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService implements UserDetailsService{
    private final UsuarioRepository repository;
    private final RolRepository repositoryRol;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean create(UsuarioDTO usuarioDTO) throws BadRequestException {

        if (repository.findByEmail(usuarioDTO.email()).isPresent()) {
            logger.error("El usuario con el email:"+ usuarioDTO.email() + " ya existe en la base de datos");
            throw new BadRequestException("El usuario con el email: " + usuarioDTO.email() + " ya existe en la base de datos");
        }
        if (repositoryRol.findById(usuarioDTO.idRol()).isEmpty()){
            logger.error("No existe un rol con el id: "+ usuarioDTO.idRol());
            throw new BadRequestException("No existe un rol con el id: "+ usuarioDTO.idRol());
        }
        UsuarioDTO userEncriptado=new UsuarioDTO(usuarioDTO.id(), usuarioDTO.nombre(), usuarioDTO.apellido(),usuarioDTO.email(),
                passwordEncoder.encode(usuarioDTO.password()), usuarioDTO.ciudad(), usuarioDTO.idRol());
        repository.save(mapToUsuario(userEncriptado));
        return true;
    }

    public List<Usuario> getAll() {
        if (repository.findAll().isEmpty()) {
            logger.info("La tabla Usuario no tiene registros");
            return null;
        }
        return repository.findAll();
    }

    public Usuario getById(Long id) throws UsuarioNotFoundException {
        return repository.findById(id).orElseThrow(UsuarioNotFoundException::new);
    }

    public Usuario getByEmail(String email) throws UsuarioNotFoundException {
        return repository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
    }

    public boolean update(UsuarioDTO usuarioDTO) throws UsuarioNotFoundException {
        if(repository.findById(usuarioDTO.id()).isEmpty()) throw new UsuarioNotFoundException();
        repository.save(mapToUsuario(usuarioDTO));
        return true;
    }

    public boolean deleteById(Long id) throws UsuarioNotFoundException {
        if(repository.findById(id).isEmpty()) throw new UsuarioNotFoundException();
        repository.deleteById(id);
        logger.info("Se elimino el registro con el id: " + id + " de la tabla Usuarios");
        return true;
    }

    private Usuario mapToUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();

        Rol rol = new Rol();
        rol.setId(usuarioDTO.idRol());

        usuario.setId(usuarioDTO.id());
        usuario.setNombre(usuarioDTO.nombre());
        usuario.setApellido(usuarioDTO.apellido());
        usuario.setEmail(usuarioDTO.email());
        usuario.setPassword(usuarioDTO.password());
        usuario.setCiudad(usuarioDTO.ciudad());
        usuario.setRol(rol);

        return usuario;
    }

    @Override
    public UserDetails loadUserByUsername(String data) {

        try {
            return repository.findByEmail(data).orElseThrow(EmailNotFoundException::new);
        } catch (EmailNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
