package com.example.digitalBooking.repository;

import com.example.digitalBooking.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad,Long> {

    @Query("select c from Ciudad c where c.nombre = :nombre")
    Optional<Ciudad> findByNombre(@Param("nombre") String nombre);
}
