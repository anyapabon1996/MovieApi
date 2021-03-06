package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Classification;
import com.example.demo.model.Gender;
import com.example.demo.model.Movies;
import com.example.demo.service.MovieService;


@RequestMapping("AnyaApi/v1")
@RestController
@CrossOrigin(origins = "http://localhost:5050") //Esto de acá es para el acceso, control y edicacion de los datos solo desde esta ruta
public class MovieController {
	
	//Atributo 
	private final MovieService movieService; 

	//Contructor 
	public MovieController(MovieService movieService) {
		
		this.movieService = movieService; 
	}
	
	//Ve todas las pelis que hay
	@GetMapping("/allmovies")
	public List<Movies> getMovies(){
		
		return this.movieService.getMovies();
	}
	
	//Crea una nueva pelicula
	@PostMapping("/createmovie")
	public boolean createMovie(@RequestBody Movies movies) {
		
		return this.movieService.createMovie(movies);
	}
	
	//Elimina una peli por id 
	@DeleteMapping("/deletemovie/{id}")
	public String deleteMovie(@PathVariable Integer id) {
		
		return this.movieService.deleteMovie(id);
	}
	
	//Encuentra una peli por id 
	@GetMapping("/moviebyid/{id}")
	public Optional<Movies> getMovieById(@PathVariable Integer id){
		
		return this.movieService.getMovieById(id);
	}
	
	//Este método devuelve un array de peliculas, vacío si no hay nada en el con el genero especificado, o error 400 si el usuario mete algo mal
	@GetMapping("/moviesbygender/{gender}")
	public List<Movies> getMoviesByGender(@PathVariable Gender gender){
		
		return this.movieService.getMoviesByGender(gender);
	}
	
	//Este metodo busca una pelicula por nombre
	@GetMapping("/moviebytitle/{title}")
	public Optional<Movies> getMovieByTitle(@PathVariable String title){
		
		return this.movieService.getMovieByTitle(title);
	}
	
	//Este metodo busca todas las pelis segun Clasificacion
	@GetMapping("/moviesClass/{classify}")
	public List<Movies> getMpvieByClassification(@PathVariable Classification classify){
	
		return this.movieService.getMoviesByClassification(classify); 
	}

	//Metodo para actualizar una pelicula
	@PutMapping("/updatemovie/{id}")
	public Optional<Movies> updateMovie(@PathVariable Integer id, @RequestBody Movies movie){
		return this.movieService.updateMovie(id, movie);
	}
	
	@GetMapping("/bestmovies")
	public List<Movies> getBestMovies(){
		System.out.println("Holaaaa");
		return this.movieService.ranking();
	}
	
	@GetMapping("/mostviewsmovies")
	public List<Movies> mostViews(){
		return this.movieService.mostViews();
	}
	
	
	
	
}
