package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.CategoriaNotFoundException;
import com.example.digitalBooking.model.Categoria;
import com.example.digitalBooking.model.dto.CategoriaDTO;
import com.example.digitalBooking.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean create(CategoriaDTO categoriaDTO) throws BadRequestException {
        if (repository.findByTitulo(categoriaDTO.titulo()).isPresent()) {
            logger.error("Ya existe una categoria con el titulo: " + categoriaDTO.titulo());
            throw new BadRequestException("Ya existe una categoria con el titulo: " + categoriaDTO.titulo());
        }
        repository.save(mapToCategoria(categoriaDTO));
        logger.info("Se creo una nueva categoria: " + categoriaDTO.titulo());
        return true;
    }

    public List<CategoriaDTO> getAll(){
        var categorias = repository.findAll();
        if (categorias.isEmpty()) {
            logger.info("La tabla Categoria no tiene registros");
            return null;
        }
        List<Object[]> productosPorCategoria = repository.countProductosByCategoriaId();

        List<CategoriaDTO> listaDTO = new ArrayList<>();
        for (Categoria categoria: categorias) {
            for (Object[] row : productosPorCategoria) {
                Long categoriaId = (Long) row[0];
                if(Objects.equals(categoriaId, categoria.getId())){
                    Long count = (Long) row[1];
                    String countCategoria = Long.toString(count);
                    String descripcionCount= countCategoria+" "+categoria.getDescripcion();
                    categoria.setDescripcion(descripcionCount);
                }

            }

            listaDTO.add(mapToDTO(categoria));
        }

        return listaDTO;
    }
    public CategoriaDTO getById(Long id) throws CategoriaNotFoundException{
        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            logger.error("No existe una categoria con el id:" + id);
            throw new CategoriaNotFoundException();
        }
        return mapToDTO(optional.get());
    }
    public CategoriaDTO getByTitulo(String titulo) throws CategoriaNotFoundException {
        var optional = repository.findByTitulo(titulo);
        if (optional.isEmpty()) {
            logger.error("No existe una categoria con el titulo:" + titulo);
            throw new CategoriaNotFoundException();
        }
        return mapToDTO(optional.get());
    }

    public boolean update(CategoriaDTO categoriaDTO) throws CategoriaNotFoundException {
        if (repository.findById(categoriaDTO.id()).isEmpty()) {
            logger.error("No existe un registro en la tabla Categoria con el id: " + categoriaDTO.id());
            throw new CategoriaNotFoundException();
        }
        repository.save(mapToCategoria(categoriaDTO));
        logger.info("Se modifico el registro con el id: " + categoriaDTO.id() + " de la tabla Categoria");
        return true;
    }

    public boolean deleteById(Long id) throws CategoriaNotFoundException {
        if(repository.findById(id).isEmpty()) throw new CategoriaNotFoundException();
        repository.deleteById(id);
        logger.info("Se elimino el registro con el id: " + id + " de la tabla Categoria");
        return true;
    }

    private Categoria mapToCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.id());
        categoria.setTitulo(categoriaDTO.titulo());
        categoria.setDescripcion(categoriaDTO.descripcion());
        categoria.setUrlImagen(categoriaDTO.urlImagen());
        return categoria;
    }

    private CategoriaDTO mapToDTO(Categoria categoria){
        return new CategoriaDTO(categoria.getId(), categoria.getTitulo(), categoria.getDescripcion(), categoria.getUrlImagen());
    }
}
