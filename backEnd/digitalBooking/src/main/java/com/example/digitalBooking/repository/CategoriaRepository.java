package com.example.digitalBooking.repository;

import com.example.digitalBooking.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    @Query("select c from Categoria c where c.titulo = :titulo")
    Optional<Categoria> findByTitulo (@Param("titulo") String titulo);
    @Query("SELECT c.id, COUNT(p) FROM Categoria c JOIN c.productos p GROUP BY c.id")
    List<Object[]> countProductosByCategoriaId();
}
