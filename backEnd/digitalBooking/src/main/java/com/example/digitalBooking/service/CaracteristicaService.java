package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CaracteristicaNotFoundException;
import com.example.digitalBooking.model.Caracteristica;
import com.example.digitalBooking.model.dto.CaracteristicaDTO;
import com.example.digitalBooking.repository.CaracteristicaRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CaracteristicaService {
    private final CaracteristicaRepository repository;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean create(CaracteristicaDTO caracteristicaDTO) throws BadRequestException {
        if (repository.findByTitulo(caracteristicaDTO.titulo()).isPresent()) {
            logger.error("Ya existe una caracteristica con el titulo: " + caracteristicaDTO.titulo());
            throw new BadRequestException("Ya existe una caracteristica con el titulo: " + caracteristicaDTO.titulo());
        }
        repository.save(mapToCaracteristica(caracteristicaDTO));
        logger.info("Se creo una nueva caracteristica: " + caracteristicaDTO.titulo());
        return true;
    }

    public List<CaracteristicaDTO> getAll(){
        var caracteristicas = repository.findAll();
        if (caracteristicas.isEmpty()) {
            logger.info("La tabla Caracteristica no tiene registros");
            return null;
        }

        List<CaracteristicaDTO> listaDTO = new ArrayList<>();
        for (Caracteristica caracteristica: caracteristicas) {
            listaDTO.add(mapToDTO(caracteristica));
        }

        return listaDTO;
    }
    public CaracteristicaDTO getById(Long id) throws CaracteristicaNotFoundException {
        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            logger.error("No existe una caracteristica con el id:" + id);
            throw new CaracteristicaNotFoundException();
        }
        return mapToDTO(optional.get());
    }
    public CaracteristicaDTO getByTitulo(String titulo) throws CaracteristicaNotFoundException {
        var optional = repository.findByTitulo(titulo);
        if (optional.isEmpty()) {
            logger.error("No existe una caracteristica con el titulo:" + titulo);
            throw new CaracteristicaNotFoundException();
        }
        return mapToDTO(optional.get());
    }

    public boolean update(CaracteristicaDTO caracteristicaDTO) throws CaracteristicaNotFoundException {
        if (repository.findById(caracteristicaDTO.id()).isEmpty()) {
            logger.error("No existe un registro en la tabla Caracteristica con el id: " + caracteristicaDTO.id());
            throw new CaracteristicaNotFoundException();
        }
        repository.save(mapToCaracteristica(caracteristicaDTO));
        logger.info("Se modifico el registro con el id: " + caracteristicaDTO.id() + " de la tabla Caracteristica");
        return true;
    }

    public boolean deleteById(Long id) throws CaracteristicaNotFoundException {
        if(repository.findById(id).isEmpty()) throw new CaracteristicaNotFoundException();
        repository.deleteById(id);
        logger.info("Se elimino el registro con el id: " + id + " de la tabla Caracteristicas");
        return true;
    }

    private Caracteristica mapToCaracteristica(CaracteristicaDTO caracteristicaDTO){
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId(caracteristicaDTO.id());
        caracteristica.setTitulo(caracteristicaDTO.titulo());
        caracteristica.setUrl(caracteristicaDTO.url());
        return caracteristica;
    }

    private CaracteristicaDTO mapToDTO(Caracteristica caracteristica){
        return new CaracteristicaDTO(caracteristica.getId(), caracteristica.getTitulo(), caracteristica.getUrl());
    }
}
