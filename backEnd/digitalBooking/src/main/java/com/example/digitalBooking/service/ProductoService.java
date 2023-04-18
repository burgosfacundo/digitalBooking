package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.model.*;
import com.example.digitalBooking.model.dto.RequestProductoDTO;
import com.example.digitalBooking.model.dto.ResponseReservaDTO;
import com.example.digitalBooking.model.dto.ResponseProductoDTO;
import com.example.digitalBooking.repository.*;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class ProductoService {
    private final ProductoRepository repository;
    private final CiudadRepository ciudadRepository;
    private final ImagenRepository imagenRepository;
    private final CategoriaRepository categoriaRepository;
    private final CaracteristicaRepository caracteristicaRepository;
    private static final Logger logger = Logger.getLogger(ProductoService.class);


    public Long create(RequestProductoDTO productoDTO) throws BadRequestException {
        if (repository.findByTitulo(productoDTO.titulo()).isPresent()) {
            logger.error("Ya existe un producto con el titulo: " + productoDTO.titulo());
            throw new BadRequestException("Ya existe una producto con el titulo: " + productoDTO.titulo());
        }
        if (ciudadRepository.findById(productoDTO.idCiudad()).isEmpty()){
            logger.error("No existe una ciudad con el id: " + productoDTO.idCiudad());
            throw new BadRequestException("No existe una ciudad con el id: " + productoDTO.idCiudad());
        }
        if (categoriaRepository.findById(productoDTO.idCategoria()).isEmpty()){
            logger.error("No existe una categoria con el id: " + productoDTO.idCategoria());
            throw new BadRequestException("No existe una categoria con el id: " + productoDTO.idCategoria());
        }
        for (Long idCaracteristica:productoDTO.caracteristicas()) {
            if (caracteristicaRepository.findById(idCaracteristica).isEmpty()){
                logger.error("No existe una caracteristica con el id: " + idCaracteristica);
                throw new BadRequestException("No existe una caracteristica con el id: " + idCaracteristica);
            }
        }
        var imageneDef=productoDTO.imagenes();
        repository.save(mapToProducto(productoDTO));
        Long idCreate=repository.findByTitulo(productoDTO.titulo()).get().getId();
        for(Imagen imagenes: imageneDef){
            Producto prod=new Producto();
            prod.setId(idCreate);
            imagenes.setProducto(prod);
            imagenRepository.save(imagenes);
        }
        logger.info("Se creo un nuevo producto: " + productoDTO.titulo());
        return repository.findByTitulo(productoDTO.titulo()).get().getId();
    }

    public List<ResponseProductoDTO> getAll(){
        var productos = repository.findAllWithImagenes();
        if (productos.isEmpty()) {
            logger.info("La tabla Producto no tiene registros");
            return null;
        }
        List<ResponseProductoDTO> listaDTO = new ArrayList<>();
        for (Producto producto:productos) {
            listaDTO.add(mapToDTO(producto));
        }
        return listaDTO;
    }

    public List<ResponseProductoDTO> getAllRand(){
        var productos = repository.findAllWithImagenesRand();
        if (productos.isEmpty()) {
            logger.info("La tabla Producto no tiene registros");
            return null;
        }

        List<ResponseProductoDTO> listaDTO = new ArrayList<>();
        for (Producto producto: productos) {
            listaDTO.add(mapToDTO(producto));
        }
        return listaDTO;
    }

    public ResponseProductoDTO getById(Long id) throws ProductoNotFoundException {
        var optionalProducto = repository.findByIdWithImagenes(id);
        if(optionalProducto.isEmpty()){
            throw new ProductoNotFoundException();
        }
        Producto producto = optionalProducto.get();
        return mapToDTO(producto);
    }
    public Producto getByTitulo(String titulo) throws ProductoNotFoundException {
        return repository.findByTitulo(titulo).orElseThrow(ProductoNotFoundException::new);
    }


    public List<ResponseProductoDTO> filterCategoria(String categoria){
        var productos = repository.filterCategoria(categoria);
        if (productos.isEmpty()) {
            logger.info("No hay registro de productos en esa categoria o no se encontro categoria");
            return null;
        }
        List<ResponseProductoDTO> listaDTO = new ArrayList<>();
        for (Producto producto: productos) {
            listaDTO.add(mapToDTO(producto));
        }
        return listaDTO;
    }
    public List<ResponseProductoDTO> filterCiudad(String ciudad){

        var productos = repository.filterCiudad(ciudad);
        if (productos.isEmpty()) {
            logger.info("No hay registro de productos en esa ciudad o no se encontro ciudad");
            return null;
        }
        List<ResponseProductoDTO> listaDTO = new ArrayList<>();
        for (Producto producto: productos) {
            listaDTO.add(mapToDTO(producto));
        }
        return listaDTO;
    }

    public List<ResponseProductoDTO> filterFechas(LocalDate fechaInicio, LocalDate fechadFin){

        var productos = repository.filterFechas(fechaInicio,fechadFin);
        if (productos.isEmpty()) {
            logger.info("No hay registro de productos disponibles en esa fecha");
            return null;
        }
        List<ResponseProductoDTO> listaDTO = new ArrayList<>();
        for (Producto producto: productos) {
            listaDTO.add(mapToDTO(producto));
        }
        return listaDTO;
    }

    public List<ResponseProductoDTO> filterCiudadAndFechas(String ciudad, LocalDate fechaInicio, LocalDate fechadFin){

        var productos = repository.filterCiudadAndFechas(ciudad,fechaInicio,fechadFin);
        if (productos.isEmpty()) {
            logger.info("No hay registro de productos en esa ciudad  y fechas ");
            return null;
        }
        List<ResponseProductoDTO> listaDTO = new ArrayList<>();
        for (Producto producto: productos) {
            listaDTO.add(mapToDTO(producto));
        }
        return listaDTO;
    }
    public void update(RequestProductoDTO productoDTO) throws ProductoNotFoundException, BadRequestException {
        if (repository.findById(productoDTO.id()).isEmpty()) {
            logger.error("No existe un registro para editar en la tabla Producto con el id: " + productoDTO.id());
            throw new ProductoNotFoundException();
        }
        if (ciudadRepository.findById(productoDTO.idCiudad()).isEmpty()){
            logger.error("No existe una ciudad con el id: " + productoDTO.idCiudad());
            throw new BadRequestException("No existe una ciudad con el id: " + productoDTO.idCiudad());
        }
        if (categoriaRepository.findById(productoDTO.idCategoria()).isEmpty()){
            logger.error("No existe una categoria con el id: " + productoDTO.idCategoria());
            throw new BadRequestException("No existe una categoria con el id: " + productoDTO.idCategoria());
        }
        Set<Caracteristica> editcaracteristicas = new HashSet<>();
        for (Long idCaracteristica:productoDTO.caracteristicas()) {
            if (caracteristicaRepository.findById(idCaracteristica).isEmpty()){
                logger.error("No existe una caracteristica con el id: " + idCaracteristica);
                throw new BadRequestException("No existe una caracteristica con el id: " + idCaracteristica);
            }
         var caractExistente= caracteristicaRepository.findById(idCaracteristica);
            editcaracteristicas.add(caractExistente.get());
        }

        var imagenesEnviadas=productoDTO.imagenes();
        if(imagenesEnviadas.isEmpty()){
            logger.error("El producto debe tener por lo menos una imagen");
            throw new BadRequestException("El producto debe tener por lo menos una imagen." );
        }

        for(Imagen imagen:imagenesEnviadas){
            if(imagenRepository.findById(imagen.getId()).isPresent()){
                imagenRepository.save(imagen);
            }
            Producto produ = new Producto();
            produ.setId(productoDTO.id());
            imagen.setProducto(produ);
            imagenRepository.save(imagen);
        }

        var producto =mapToProducto(productoDTO);
        producto.setCaracteristicas(editcaracteristicas);
        producto.setImagenes(imagenesEnviadas);

        repository.save(producto);
        logger.info("Se modifico el registro con el id: " + productoDTO.id() + " de la tabla Producto");
    }

    public boolean deleteById(Long id) throws ProductoNotFoundException {
        if(repository.findById(id).isEmpty()) throw new ProductoNotFoundException();
        repository.deleteById(id);
        logger.info("Se elimino el registro con el id: " + id + " de la tabla Productos");
        return true;
    }

    private Producto mapToProducto(RequestProductoDTO productoDTO){
        Producto producto = new Producto();

        Set<Caracteristica> caracteristicas = new HashSet<>();
        for (Long idCaracteristica:productoDTO.caracteristicas()) {
            Caracteristica caracteristica = new Caracteristica();
            caracteristica.setId(idCaracteristica);
            caracteristicas.add(caracteristica);
        }

        Ciudad ciudad = new Ciudad();
        ciudad.setId(productoDTO.idCiudad());
        Categoria categoria = new Categoria();
        categoria.setId(productoDTO.idCategoria());

        producto.setId(productoDTO.id());
        producto.setTitulo(productoDTO.titulo());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setDireccion(productoDTO.direccion());
        producto.setLatitud(productoDTO.latitud());
        producto.setLongitud(productoDTO.longitud());
        producto.setNormas(productoDTO.normas());
        producto.setSaludYseguridad(productoDTO.saludYseguridad());
        producto.setCancelacion(productoDTO.cancelacion());
        producto.setCiudad(ciudad);
        producto.setCategoria(categoria);
        producto.setCaracteristicas(caracteristicas);
        return producto;
    }

    private ResponseProductoDTO mapToDTO(Producto producto){
        var ciudad = producto.getCiudad();
        var categoria = producto.getCategoria();

        Set<Caracteristica> caracteristicas = new HashSet<>();
        for(Caracteristica caracteristica:producto.getCaracteristicas()) {
            caracteristicas.add(caracteristica);
        }

        Set<Imagen> imagenes = new HashSet<>();
        for(Imagen imagen:producto.getImagenes()) {
            imagenes.add(imagen);
        }

        Set<ResponseReservaDTO> reservas = new HashSet<>();
        for(Reserva reserva:producto.getReservas()) {
            var dto= new ResponseReservaDTO(reserva.getId(),reserva.getHoraComienzo(),reserva.getFechaInicial(),
                    reserva.getFechaFinal(),reserva.getUsuario().getId(),reserva.getProducto().getId(), producto.getTitulo(), producto.getImagenes());
            reservas.add(dto);
        }

        return new ResponseProductoDTO(producto.getId(), producto.getTitulo(), producto.getDescripcion(), producto.getDescripcion(), producto.getLatitud(),
                producto.getLongitud(), producto.getNormas(), producto.getSaludYseguridad(), producto.getCancelacion(),
                ciudad,categoria,caracteristicas,imagenes,reservas);
    }
}