package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CiudadNotFoundException;
import com.example.digitalBooking.model.Ciudad;
import com.example.digitalBooking.model.dto.CiudadDTO;
import com.example.digitalBooking.repository.CiudadRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CiudadService {

    private final CiudadRepository repository;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean create(CiudadDTO ciudadDTO) throws BadRequestException {
        if (repository.findByNombre(ciudadDTO.nombre()).isPresent()) {
            logger.error("Ya existe una ciudad con el nombre: " + ciudadDTO.nombre());
            throw new BadRequestException("Ya existe una ciudad con el nombre: " + ciudadDTO.nombre());
        }
        repository.save(mapToCategoria(ciudadDTO));
        logger.info("Se creo una nueva ciudad: " + ciudadDTO.nombre());
        return true;
    }

    public List<CiudadDTO> getAll(){
        var ciudades = repository.findAll();
        if (ciudades.isEmpty()) {
            logger.info("La tabla Ciudad no tiene registros");
            return null;
        }

        List<CiudadDTO> listaDTO = new ArrayList<>();
        for (Ciudad ciudad: ciudades) {
            listaDTO.add(mapToDTO(ciudad));
        }

        return listaDTO;
    }
    public CiudadDTO getById(Long id) throws CiudadNotFoundException {
        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            logger.error("No existe una ciudad con el id:" + id);
            throw new CiudadNotFoundException();
        }
        return mapToDTO(optional.get());
    }
    public CiudadDTO getByNombre(String nombre) throws CiudadNotFoundException {
        var optional = repository.findByNombre(nombre);
        if (optional.isEmpty()) {
            logger.error("No existe una ciudad con el nombre:" + nombre);
            throw new CiudadNotFoundException();
        }
        return mapToDTO(optional.get());
    }

    public boolean update(CiudadDTO ciudadDTO) throws CiudadNotFoundException {
        if (repository.findById(ciudadDTO.id()).isEmpty()) {
            logger.error("No existe un registro en la tabla Ciudad con el id: " + ciudadDTO.id());
            throw new CiudadNotFoundException();
        }
        repository.save(mapToCategoria(ciudadDTO));
        logger.info("Se modifico el registro con el id: " + ciudadDTO.id() + " de la tabla Ciudad");
        return true;
    }

    public boolean deleteById(Long id) throws CiudadNotFoundException {
        if(repository.findById(id).isEmpty()) throw new CiudadNotFoundException();
        repository.deleteById(id);
        logger.info("Se elimino el registro con el id: " + id + " de la tabla Ciudad");
        return true;
    }
    private Ciudad mapToCategoria(CiudadDTO ciudadDTO){
        Ciudad ciudad = new Ciudad();
        ciudad.setId(ciudadDTO.id());
        ciudad.setNombre(ciudadDTO.nombre());
        return ciudad;
    }

    private CiudadDTO mapToDTO(Ciudad ciudad){
        return new CiudadDTO(ciudad.getId(), ciudad.getNombre());
    }
}
