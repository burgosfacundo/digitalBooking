package com.example.digitalBooking.service;

import com.example.digitalBooking.exception.BadRequestException;
import com.example.digitalBooking.exception.ProductoNotFoundException;
import com.example.digitalBooking.exception.UsuarioNotFoundException;
import com.example.digitalBooking.model.*;
import com.example.digitalBooking.model.dto.RequestReservaDTO;
import com.example.digitalBooking.model.dto.ResponseReservaDTO;
import com.example.digitalBooking.repository.ProductoRepository;
import com.example.digitalBooking.repository.ReservaRepository;
import com.example.digitalBooking.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class ReservaService {

    private final ReservaRepository repository;
    private final ProductoRepository productoRepository;
    private JavaMailSender javaMailSender;
    private final UsuarioRepository usuarioRepository;

    private static final Logger logger = Logger.getLogger(ProductoService.class);

    public boolean create(RequestReservaDTO requestReservaDTO) throws BadRequestException, MessagingException {
        if (productoRepository.findById(requestReservaDTO.idProducto()).isEmpty()) {
            logger.error("No existe un producto con el id:" + requestReservaDTO.idProducto());
            throw new BadRequestException("No existe una producto con el id: " + requestReservaDTO.idProducto());
        }

        if (usuarioRepository.findByEmail(requestReservaDTO.emailUsuario()).isEmpty()) {
            logger.error("No existe un usuario con el email:" + requestReservaDTO.emailUsuario());
            throw new BadRequestException("No existe un usuario con el email: " + requestReservaDTO.emailUsuario());
        }

        repository.save(mapToReserva(requestReservaDTO));
        logger.info("Se creo una nueva reserva");
        sendEmail(requestReservaDTO.emailUsuario(),requestReservaDTO.fechaInicial(),requestReservaDTO.horaComienzo());
        return true;
    }

    public List<ResponseReservaDTO> getAllByIdProducto(Long idProducto) throws ProductoNotFoundException {
        if (productoRepository.findById(idProducto).isEmpty()) {
            logger.error("No existe un producto con el id:" + idProducto);
            throw new ProductoNotFoundException();
        }

        var reservas = repository.findAllByProductoId(idProducto);
        List<ResponseReservaDTO> listaDTO = new ArrayList<>();
        for (Reserva reserva:reservas) {
            listaDTO.add(mapToDTO(reserva));
        }
        return listaDTO;
    }


    public List<ResponseReservaDTO> findAllByIdUsuario(Long idUser) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(idUser).isEmpty()) {
            logger.error("No exista una reserva con el usuario :" + idUser);
            throw new UsuarioNotFoundException();
        }

        var reservas = repository.findAllByIdUsuario(idUser);
        List<ResponseReservaDTO> listaDTO = new ArrayList<>();
        for (Reserva reserva:reservas) {
            listaDTO.add(mapToDTO(reserva));
        }
        return listaDTO;
    }



    private ResponseReservaDTO mapToDTO(Reserva reserva) {
        return new ResponseReservaDTO(reserva.getId(),reserva.getHoraComienzo(), reserva.getFechaInicial(),
                reserva.getFechaFinal(),reserva.getUsuario().getId(),reserva.getProducto().getId(),reserva.getProducto().getTitulo(),reserva.getProducto().getImagenes());
    }

    private Reserva mapToReserva(RequestReservaDTO requestReservaDTO) {
        Reserva reserva = new Reserva();

        reserva.setId(requestReservaDTO.id());
        reserva.setHoraComienzo(requestReservaDTO.horaComienzo());
        reserva.setFechaInicial(requestReservaDTO.fechaInicial());
        reserva.setFechaFinal(requestReservaDTO.fechaFinal());

        Producto producto = new Producto();
        producto.setId(requestReservaDTO.idProducto());
        reserva.setProducto(producto);

        Usuario usuario = new Usuario();
        var user = usuarioRepository.findByEmail(requestReservaDTO.emailUsuario());
        usuario.setId(user.get().getId());
        reserva.setUsuario(usuario);

        return reserva;
    }

    private void sendEmail(String Email, LocalDate fechaInicio, LocalTime horaInicio) throws MessagingException {
        var user=usuarioRepository.findByEmail(Email);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = horaInicio.format(formatoHora);
        String fechFormateada=fechaInicio.format(formatoFecha);
        String messageEmail="Hola " + user.get().getNombre()+ " "+user.get().getApellido()+ " "+ "se agendo tu reserva el dia "+fechFormateada+ " a las "+ horaFormateada;

        helper.setTo(Email);
        helper.setSubject("Se agendo tu reserva!!!");
        helper.setText(messageEmail, false);
        javaMailSender.send(message);
    }
}
