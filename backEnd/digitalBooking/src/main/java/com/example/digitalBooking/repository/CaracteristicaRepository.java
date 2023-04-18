package com.example.digitalBooking.repository;

import com.example.digitalBooking.model.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica,Long> {

    @Query("select c from Caracteristica c where c.titulo = :titulo")
    Optional<Caracteristica> findByTitulo(@Param("titulo") String titulo);
}
