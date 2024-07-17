package com.example.aluracursos.literalura;

import com.example.aluracursos.literalura.Principal.Principal;
import com.example.aluracursos.literalura.Repository.IAutorReposity;
import com.example.aluracursos.literalura.Repository.ILibroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private ILibroRepository repositoryLibro;
	@Autowired
	private IAutorReposity repositoryAutor;


	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal p = new Principal(repositoryLibro, repositoryAutor);
		p.menu();
	}
}
