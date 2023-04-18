package com.example.digitalBooking.repository;

import com.example.digitalBooking.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,Long> {

    @Query("select i from Imagen i where i.titulo = :titulo")
    Optional<Imagen> findByTitulo (@Param("titulo") String titulo);
}
