package com.example.digitalBooking.model.dto;

import com.example.digitalBooking.model.*;
import java.util.Set;
public record ResponseProductoDTO (Long id, String titulo, String descripcion,String direccion, Float latitud,
                                   Float longitud,String normas,String saludYseguridad, String cancelacion,
                                   Ciudad ciudad, Categoria categoria, Set<Caracteristica> caracteristicas,
                                   Set<Imagen> imagenes,Set<ResponseReservaDTO> reservas){
}
