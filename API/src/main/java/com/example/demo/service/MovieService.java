package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.example.demo.model.Classification;
import com.example.demo.model.Gender;
import com.example.demo.model.Message;
import com.example.demo.model.Movies;

@Service
public class MovieService {

	//Atributo 
	private List<Movies> listMovies; 
	private boolean control; 
//	List<Movies> bestRankingMovies = new ArrayList<>(); 
//	List<Movies> bestMoviesRankintToReturn = new ArrayList<>(); 
	
	Movies HarryPoter1 = new Movies(1, "Harry Potter and the sorcerer's stone", 5.0, false, Gender.Fantasy, Classification.ATP, 2001);
	Movies HarryPoter2 = new Movies(2, "Harry Potter", 5.0, false, Gender.Action, Classification.ATP, 2001);

	
	Message message1 = new Message();
	
	//Constructor 
	public MovieService (List<Movies> listMovies) {
		
		this.listMovies = listMovies; 
	}

	
	//Metodo para obtener todas las peliculas
	public List<Movies> getMovies() {
		
//		this.listMovies.add(HarryPoter1);
//		this.listMovies.add(HarryPoter2);
		
		return this.listMovies;
	}
	
	
	//Metodo para crear una pelicula 
	public boolean createMovie(Movies movie) {
		
		this.control = false; 
		
		Iterator <Movies> itr = this.listMovies.iterator();
		
		//Recorremos la lista
		while (itr.hasNext()) {
			
			if (itr.next().getId() == movie.getId()) {
				
				this.control = true; 
				
				break; 
			}
		}
		
		
		if (control) {
			
			return false;
			
		} else {
			
			this.listMovies.add(movie);
			
			return true; 
		}
	}
	

	//Metodo para eliminar de mi lista
	public String deleteMovie(Integer id) {
		
		this.control = false; 

		//Recorremos lalista 
		for (Movies movie : this.listMovies) {
			if (movie.getId() == id) {
				this.listMovies.remove(movie); 
				this.control = true; 
				break; 
			}
		} 
		
		if (control) return "Successful remove";
		
		else return "Not found movie with that ID";

	}


	//Metodo para buscar una peli por id
	public Optional<Movies> getMovieById(Integer id) {
		
		Optional <Movies> mo =  null; 
		
		//Intentamos buscar nuestra pelicula. En caso de no hallar, la deja vacia 
		try {
			mo = Optional.ofNullable(this.listMovies.stream().filter(m -> m.getId() == id).findFirst().get());
			
		//En caso de que no se halle la peli solicitada, se le tira este error al usuario.
		} catch (NoSuchElementException nsee) {
			
			//this.message1.logMessage(1); -> Tiene un error.
			System.out.println("you've putted a not existent id into our data bases");
			System.out.print(nsee.getCause());
		}
		
		return mo;
	}


	//Metodo que busca peliculas por genero. 
	public List<Movies> getMoviesByGender(Gender gender) {
		
			List <Movies> listByGender = this.listMovies.
					stream().
					filter(m -> m.getGender().equals(gender)).
					collect(Collectors.toList());
			
			return listByGender;
	}
	
	
	//Funcion que busca por nombre una pelicula
	public Optional<Movies> getMovieByTitle(String title){
				
//		title = title.toLowerCase().trim();
		
		System.out.println(title);
		
		if ((title.toLowerCase().trim()).equals(this.listMovies.get(0).getTitle().toLowerCase())) System.out.println(4);

		Optional<Movies> mo = null; 
		
		try {
			
			mo = Optional.ofNullable(this.listMovies.
					stream().
					filter(m -> (m.getTitle().toLowerCase()).equals(title.toLowerCase().trim())).
					findFirst().
					get());
			
		} catch (NoSuchElementException nsee) {
			
			System.out.println("You're looking for an unexisting movie");
			System.out.print(nsee.getCause());
		}
		return mo;
		
	}


	public List<Movies> getMoviesByClassification(Classification classify) {
		
		//Buscamos las pelis de esta clasificacion
		List <Movies> listByClassify = this.listMovies.
				stream().
				filter(m -> m.getClassification().equals(classify)).
				collect(Collectors.toList());
		
		return listByClassify;
		
	}
	
	
	//Metodo para actualizar una pelicula particular
	public Optional<Movies> updateMovie(Integer id, Movies movies){
		
		Optional<Movies> mo = null;
		
		try {
			
			mo = Optional.ofNullable(
					this.listMovies.
					stream().
					filter(m -> m.getId() == id).
					findFirst().
					get());
			
		} catch (NoSuchElementException nsee) {
			
			System.out.println("you've putted a not existent id into our data bases");
			
			System.out.print(nsee.getCause());
			
			return null;
		}
	
			
		for (int i = 0; i<this.listMovies.size(); i++) {
				
			if (this.listMovies.get(i).getId() == id) {
				
				//Este metodo se me queda corto. No sé cómo pasar el valor booleando Adultsonly, 
				//Anotacion: lo hice yo fuera de lombok, pero es más codigo. 
//				this.listMovies.get(i).setAdultsOnly(movies.getAdultsOnly());
//				this.listMovies.get(i).setClassification(movies.getClassification());
//				this.listMovies.get(i).setGender(movies.getGender());
//				this.listMovies.get(i).setPremiere(movies.getPremiere());
//				this.listMovies.get(i).setQualification(movies.getQualification());
//				this.listMovies.get(i).setTitle(movies.getTitle());
				
				movies.setId(id);
				this.listMovies.remove(i);
				this.listMovies.add(i, movies);
			}
		}
		
		return mo;
	}
	
	
	//Metodo para buscar las pelis con mejores rakings
	public List<Movies> ranking(){
		
		List<Movies> bestRankingMovies = new ArrayList<>(); 
		List<Movies> bestMoviesRankingToReturn = new ArrayList<>(); 
		
		double max = 0.0; 
		
		int value, auxiliar1 = 0, auxiliar2 = 0;
		
		//Buscamos el valor mas grande
		for (Movies m : this.listMovies) {
			if (m.getQualification() >= max) max = m.getQualification();
		}
		
		//Agregamos todas las pelis con calificacion maxima en una lista general 
		for (Movies m : this.listMovies) {
			if (m.getQualification() == max) {
				bestRankingMovies.add(m); 
			}
		}
		
		//Filtramos solo tres peliculas con valor maximo en caso de q haya mas de tres.		
		if (bestRankingMovies.size() > 3) {
			
			this.control = true;
				
				for (int i=0; i<3; i++) {
					
					value = (int) (Math.random()*bestRankingMovies.size()); 
					
					if (i==0) auxiliar1 = value; 
					if (i==1) auxiliar2 = value;
					
					if (i != 0) {
						
						while (value == auxiliar1 || value == auxiliar2) {
							value = (int) (Math.random()*bestRankingMovies.size()); 
						}
					}
					
					//Este metodo Siempre me va a devolver las primeras tres del ranking, no un numero random cualquiera
					bestMoviesRankingToReturn.add(bestRankingMovies.get(value));
				}
			
			//Si hay menos de tres pelis con ranking maximo, devolvemos lo que hay
		} else return bestRankingMovies; 
		
		//Si habia mas de tres, devolvemos las tres seleccionadas
		return bestMoviesRankingToReturn;
		
	}
	
}
