package com.example.digitalBooking.repository;

import com.example.digitalBooking.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT DISTINCT p FROM Producto p LEFT JOIN FETCH p.imagenes")
    List<Producto> findAllWithImagenes();

    @Query("SELECT DISTINCT p FROM Producto p LEFT JOIN FETCH p.imagenes ORDER BY RAND()")
    List<Producto> findAllWithImagenesRand();

    @Query("SELECT p FROM Producto p WHERE p.titulo = :titulo")
    Optional<Producto> findByTitulo (@Param("titulo") String titulo);

    @Query("SELECT DISTINCT p FROM Producto p LEFT JOIN FETCH p.imagenes WHERE p.id = :id")
    Optional<Producto> findByIdWithImagenes(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Producto p JOIN FETCH p.categoria c LEFT JOIN FETCH p.imagenes WHERE c.titulo = :titulo")
    List<Producto> filterCategoria (@Param("titulo")String titulo);

    @Query("SELECT DISTINCT p FROM Producto p JOIN FETCH p.ciudad c LEFT JOIN FETCH p.imagenes WHERE c.nombre = :nombreCiudad")
    List<Producto> filterCiudad (@Param("nombreCiudad")String nombreCiudad);

    @Query("SELECT DISTINCT p FROM Producto p JOIN FETCH p.ciudad c LEFT JOIN FETCH p.imagenes LEFT JOIN p.reservas r " +
            "WHERE NOT EXISTS (SELECT rs FROM Reserva rs WHERE rs.producto = p AND rs.fechaInicial >= :fechaInicio AND rs.fechaFinal <= :fechaFin)")
    List<Producto> filterFechas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT DISTINCT p FROM Producto p JOIN FETCH p.ciudad c LEFT JOIN FETCH p.imagenes WHERE c.nombre = :nombreCiudad " +
            "AND NOT EXISTS (SELECT r FROM p.reservas r WHERE r.fechaInicial >= :fechaInicio AND r.fechaFinal <= :fechaFin)")
    List<Producto> filterCiudadAndFechas(@Param("nombreCiudad") String nombreCiudad, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}
