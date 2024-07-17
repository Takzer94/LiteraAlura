package com.example.aluracursos.literalura.Principal;

import com.example.aluracursos.literalura.Models.Autor;
import com.example.aluracursos.literalura.Models.Idioma;
import com.example.aluracursos.literalura.Models.Libro;
import com.example.aluracursos.literalura.Models.Records.AutorRecord;
import com.example.aluracursos.literalura.Models.Records.DatosGeneralesRecord;
import com.example.aluracursos.literalura.Models.Records.LibroRecord;
import com.example.aluracursos.literalura.Repository.IAutorReposity;
import com.example.aluracursos.literalura.Repository.ILibroRepository;
import com.example.aluracursos.literalura.Services.ConsumoAPI;
import com.example.aluracursos.literalura.Services.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos convertirDatos = new ConvertirDatos();
    private Scanner sc = new Scanner(System.in);
    private ILibroRepository libroRepository;
    private IAutorReposity iAutorReposity;

    @Autowired
    public Principal(ILibroRepository libroRepository, IAutorReposity iAutorReposity) {
        this.libroRepository = libroRepository;
        this.iAutorReposity = iAutorReposity;
    }

    public void menu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println(" *** Biblioteca LiterAlura *** ");
            System.out.println("1. Buscar libro por titulo. ");
            System.out.println("2. Mostrar libros registrados.");
            System.out.println("3. Mostrar autores registrados.");
            System.out.println("4. Mostrar autores vivos durante x año.");
            System.out.println("5. Mostrar libros por idioma.");
            System.out.println("0. Salir.");
            System.out.print("Ingrese la opcion deseada: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opcion no válida");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error. Por favor, ingrese un número válido.");
                sc.next();
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
    }

    private void buscarLibro() {
        try {
            System.out.print("Ingresa el título del libro: ");
            String tituloLibro = sc.next().toLowerCase();
            String url = "https://gutendex.com/books/?search=" + tituloLibro.replace(" ", "%20");
            System.out.print("Espera un momento...");
            var json = consumoAPI.obtenerDatos(url);
            var datos = convertirDatos.obtenerDatos(json, DatosGeneralesRecord.class);

            Optional<LibroRecord> libroBuscado = datos.resultados().stream()
                    .filter(e -> e.titulo().toLowerCase().contains(tituloLibro))
                    .findFirst();

            if (libroBuscado.isPresent()) {
                System.out.println("*** Libro encontrado ***");

                AutorRecord autorRecord = libroBuscado.get().autor().getFirst();
                Optional<Autor> autorOptional = iAutorReposity.findByNombre(autorRecord.nombre());

                Autor autor;
                if (autorOptional.isPresent()) {
                    autor = autorOptional.get();
                } else {
                    autor = new Autor(autorRecord);
                    autor = iAutorReposity.save(autor);
                }

                Optional<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCase(libroBuscado.get().titulo());
                if (libroExistente.isPresent()) {
                    System.out.println(libroExistente.get());
                } else {
                    Libro libro = new Libro(libroBuscado.get());
                    libro.setAutor(autor);
                    libroRepository.save(libro);
                    System.out.println(libro);
                    System.out.println("*** Libro guardado exitosamente. ***");
                }
            } else {
                System.out.println("No se encontró el libro :(");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        try {
            System.out.println("*** Libros registrados ***");
            List<Libro> libros = libroRepository.findAll();
            if (!libros.isEmpty()) {
                libros.forEach(System.out::println);
            } else {
                System.out.println("No hay libros registrados.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    private void listarAutoresRegistrados() {
        try {
            System.out.println("*** Los autores registrados son ***");
            List<Autor> autores = iAutorReposity.findAll();
            if (!autores.isEmpty()) {
                autores.forEach(System.out::println);
            } else {
                System.out.println("No hay autores registrados.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    private void listarAutoresVivos() {
        try {
            System.out.print("Escribe el año del autor: ");
            int fecha = sc.nextInt();
            sc.nextLine();
            List<Autor> autores = iAutorReposity.autoresVivos(fecha);
            if (!autores.isEmpty()) {
                autores.forEach(System.out::println);
            } else {
                System.out.println("No se encontraron autores vivos.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    private void listarLibrosPorIdioma() {
        try {
            System.out.print("""
                Idiomas disponibles:
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
            System.out.print("Ingresa el que deseas: ");
            String codigoIdioma = sc.next().toLowerCase();

            var idioma = Idioma.fromString2(codigoIdioma);
            List<Libro> buscarIdioma = libroRepository.findByIdioma(idioma);
            if(!buscarIdioma.isEmpty()){
                System.out.println("Los libros en el idioma " + idioma + " son:");
                buscarIdioma.forEach(System.out::println);
            } else {
                System.out.println("No hay libros en dicho idioma.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
