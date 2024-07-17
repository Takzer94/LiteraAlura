package com.example.aluracursos.literalura.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum Idioma {
    es("[es]", "es"),
    en("[en]", "en"),
    fr("[fr]", "fr"),
    it("[it]", "it"),
    pt("[pt]", "pt"),
    fi("[fi]", "fi");

    private String idioma;
    private String idioma2;


    Idioma(String idioma, String idioma2) {
        this.idioma = idioma;
        this.idioma2 = idioma2;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idioma.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public static Idioma fromString2(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idioma2.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }
    @JsonValue
    public String getIdioma() {
        return idioma;
    }

    public String getIdioma2() {
        return idioma2;
    }
}
