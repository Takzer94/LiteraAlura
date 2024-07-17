package com.example.aluracursos.literalura.Services;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
