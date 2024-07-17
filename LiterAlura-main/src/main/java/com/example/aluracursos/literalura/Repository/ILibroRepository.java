package com.example.aluracursos.literalura.Repository;

import com.example.aluracursos.literalura.Models.Idioma;
import com.example.aluracursos.literalura.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
    //Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByIdioma(Idioma idioma2);

    //@Query("SELECT l FROM Libro l ORDER BY l.numero_descargas DESC LIMIT 10")
    //List<Libro> top10LibrosMasDescargados();

}
