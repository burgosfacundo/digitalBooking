package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ImagenNotFoundException;
import com.example.digitalBooking.model.Imagen;
import com.example.digitalBooking.model.Producto;
import com.example.digitalBooking.model.dto.ImagenDTO;
import com.example.digitalBooking.repository.ImagenRepository;
import com.example.digitalBooking.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ImagenService {
    private final ImagenRepository repository;
    private final ProductoRepository productoRepository;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean create(ImagenDTO imagenDTO) throws BadRequestException{
        if (repository.findByTitulo(imagenDTO.titulo()).isPresent()) {
            logger.error("Ya existe una imagen con el titulo: " + imagenDTO.titulo());
            throw new BadRequestException("Ya existe una imagen con el titulo: " + imagenDTO.titulo());
        }
        if(productoRepository.findById(imagenDTO.idProducto()).isEmpty()){
            logger.error("No existe un producto con el id: " + imagenDTO.idProducto());
            throw new BadRequestException("No existe un producto con el id:" + imagenDTO.idProducto());
        }
        repository.save(mapToImagen(imagenDTO));
        logger.info("Se creo una nueva imagen: " + imagenDTO.titulo());
        return true;
    }

    public List<ImagenDTO> getAll(){
        var imagenes = repository.findAll();
        if (imagenes.isEmpty()) {
            logger.info("La tabla Imagen no tiene registros");
            return null;
        }

        List<ImagenDTO> listaDTO = new ArrayList<>();
        for (Imagen imagen: imagenes) {
            listaDTO.add(mapToDTO(imagen));
        }

        return listaDTO;
    }
    public ImagenDTO getById(Long id) throws ImagenNotFoundException {
        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            logger.error("No existe una imagen con el id:" + id);
            throw new ImagenNotFoundException();
        }
        return mapToDTO(optional.get());
    }
    public ImagenDTO getByTitulo(String titulo) throws ImagenNotFoundException {
        var optional = repository.findByTitulo(titulo);
        if (optional.isEmpty()) {
            logger.error("No existe una imagen con el titulo:" + titulo);
            throw new ImagenNotFoundException();
        }
        return mapToDTO(optional.get());
    }

    public boolean update(ImagenDTO imagenDTO) throws ImagenNotFoundException {
        if (repository.findById(imagenDTO.id()).isEmpty()) {
            logger.error("No existe un registro en la tabla Imagen con el id: " + imagenDTO.id());
            throw new ImagenNotFoundException();
        }
        repository.save(mapToImagen(imagenDTO));
        logger.info("Se modifico el registro con el id: " + imagenDTO.id() + " de la tabla Imagen");
        return true;
    }

    public boolean deleteById(Long id) throws ImagenNotFoundException {
        if(repository.findById(id).isEmpty()) throw new ImagenNotFoundException();
        repository.deleteById(id);
        logger.info("Se elimino el registro con el id: " + id + " de la tabla Imagen");
        return true;
    }

    private Imagen mapToImagen(ImagenDTO imagenDTO){
        Imagen imagen = new Imagen();
        Producto producto = new Producto();
        producto.setId(imagenDTO.idProducto());
        imagen.setId(imagenDTO.id());
        imagen.setTitulo(imagenDTO.titulo());
        imagen.setUrl(imagenDTO.url());
        imagen.setProducto(producto);
        return imagen;
    }

    private ImagenDTO mapToDTO(Imagen imagen){
        return new ImagenDTO(imagen.getId(), imagen.getTitulo(), imagen.getUrl(),imagen.getProducto().getId());
    }
}
