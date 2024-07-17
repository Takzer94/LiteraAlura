package com.example.aluracursos.literalura.Models.Records;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroRecord(
        @JsonAlias("id") Integer id,
        @JsonAlias("title")String titulo,
        @JsonAlias("authors") List<AutorRecord> autor,
        @JsonAlias("languages")List<String> idioma,
        @JsonAlias("download_count")Integer descargas
) {}
