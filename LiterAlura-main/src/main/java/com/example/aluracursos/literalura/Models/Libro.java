package com.example.aluracursos.literalura.Models;


import com.example.aluracursos.literalura.Models.Records.AutorRecord;
import com.example.aluracursos.literalura.Models.Records.LibroRecord;
import jakarta.persistence.*;

import java.util.Optional;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer descargas;

    public Libro(){}


    public Libro(LibroRecord lr) {
        this.titulo = lr.titulo();
        //this.autor = autor;
        this.idioma = Idioma.fromString(lr.idioma().toString().split(",")[0].trim());
        this.descargas = lr.descargas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return String.format("""
        *** Libro ***
        Titulo: %s
        Autor: %s
        Idioma: %s
        Descargas: %d
        """, titulo, autor != null ? autor.getNombre() : "N/A", idioma, descargas);
    }

}