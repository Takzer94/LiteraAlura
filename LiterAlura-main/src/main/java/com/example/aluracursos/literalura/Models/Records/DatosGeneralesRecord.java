package com.example.aluracursos.literalura.Models.Records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosGeneralesRecord(@JsonAlias("results") List<LibroRecord> resultados) {
}
