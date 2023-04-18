package com.example.digitalBooking.repository;


import com.example.digitalBooking.model.Puntuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface PuntuacionRepository extends JpaRepository<Puntuacion,Long> {

    @Query("SELECT p FROM Puntuacion p WHERE p.producto.id = :idProducto")
    List<Puntuacion> findAllByProductoId(@Param("idProducto") Long idProducto);
}
