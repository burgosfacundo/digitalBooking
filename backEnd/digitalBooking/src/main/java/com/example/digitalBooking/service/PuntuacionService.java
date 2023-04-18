package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.Producto;
import com.example.digitalBooking.model.Puntuacion;
import com.example.digitalBooking.model.Usuario;
import com.example.digitalBooking.model.dto.PuntuacionDTO;
import com.example.digitalBooking.repository.ProductoRepository;
import com.example.digitalBooking.repository.PuntuacionRepository;
import com.example.digitalBooking.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PuntuacionService {
    private final PuntuacionRepository repository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private static final Logger logger = Logger.getLogger(CategoriaService.class);

    public boolean create(PuntuacionDTO puntuacionDTO) throws BadRequestException {
        if (productoRepository.findById(puntuacionDTO.idProducto()).isEmpty()) {
            logger.error("No existe un producto con el id:" + puntuacionDTO.idProducto());
            throw new BadRequestException("No existe una producto con el id: " + puntuacionDTO.idProducto());
        }
        if (usuarioRepository.findById(puntuacionDTO.idUsuario()).isEmpty()) {
            logger.error("No existe un usuario con el id:" + puntuacionDTO.idUsuario());
            throw new BadRequestException("No existe un usuario con el id: " + puntuacionDTO.idUsuario());
        }

        repository.save(mapToPuntuacion(puntuacionDTO));
        logger.info("Se creo una nueva puntuacion");
        return true;
    }

    public List<PuntuacionDTO> getAllByIdProducto(Long idProducto) throws ProductoNotFoundException {
        if (productoRepository.findById(idProducto).isEmpty()) {
            logger.error("No existe un producto con el id:" + idProducto);
            throw new ProductoNotFoundException();
        }

        var puntuaciones = repository.findAllByProductoId(idProducto);
        List<PuntuacionDTO> listaDTO = new ArrayList<>();
        for (Puntuacion puntuacion:puntuaciones) {
            listaDTO.add(mapToDTO(puntuacion));
        }
        return listaDTO;
    }


    private Puntuacion mapToPuntuacion(PuntuacionDTO puntuacionDTO){
        Puntuacion puntuacion = new Puntuacion();
        Usuario usuario = new Usuario();
        usuario.setId(puntuacionDTO.idUsuario());
        Producto producto = new Producto();
        producto.setId(puntuacionDTO.idProducto());
        puntuacion.setId(puntuacionDTO.id());
        puntuacion.setPuntuacion(puntuacionDTO.puntuacion());
        puntuacion.setUsuario(usuario);
        puntuacion.setProducto(producto);
        return puntuacion;
    }

    private PuntuacionDTO mapToDTO(Puntuacion puntuacion){
        return new PuntuacionDTO(puntuacion.getId(),puntuacion.getPuntuacion(),
                puntuacion.getUsuario().getId(), puntuacion.getProducto().getId());
    }
}
