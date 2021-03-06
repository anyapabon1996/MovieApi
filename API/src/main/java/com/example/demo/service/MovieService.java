package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.example.demo.model.Classification;
import com.example.demo.model.ComparatorMovies;
import com.example.demo.model.Gender;
import com.example.demo.model.Message;
import com.example.demo.model.Movies;
import com.example.demo.model.Views;

@Service
public class MovieService {

	//Atributo 
	private List<Movies> listMovies; 
	private boolean control; 
	private List<Views> listViews;
	
//	Movies HarryPoter1 = new Movies(1, "Harry Potter and the sorcerer's stone", 5.0, false, Gender.Fantasy, Classification.ATP, 2001);
//	Movies HarryPoter2 = new Movies(2, "Harry Potter", 5.0, false, Gender.Action, Classification.ATP, 2001);
	
	Views view = new Views ();

	
	Message message1 = new Message();
	
	//Constructor 
	public MovieService (List<Movies> listMovies, List<Views> listViews) {
		
		this.listMovies = listMovies; 
		
		this.listViews = listViews;
	}

	
	//Metodo para obtener todas las peliculas
	public List<Movies> getMovies() {
		
//		this.listMovies.add(HarryPoter1);
//		this.listMovies.add(HarryPoter2);
		
		//Ordena de modo creciente la lista.
		//Esto es, a la lista, le aplica el método comparator que yo hice. 
		Collections.sort(this.listMovies, new ComparatorMovies());
				
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
		
		this.control = false;
		
		Optional <Movies> mo =  null; 
		
		Integer auxiliarValue = 0;

		//Intentamos buscar nuestra pelicula. En caso de no hallar, la deja vacia 
		try {
			mo = Optional.ofNullable(this.listMovies.stream().filter(m -> m.getId() == id).findFirst().get());
						
			//Buscamos si la peli buscada ya fue vista antes. Si lo fue, le sumamos 1 en vistaS
			if (this.listViews.size() > 0) {
				
				for (int i = 0; i < this.listViews.size(); i++) {
					
					if (this.listViews.get(i).getId() == id) {
						
						this.control = true;
						
						auxiliarValue = this.listViews.get(i).getValue();
						
						auxiliarValue++;
						
						this.listViews.get(i).setValue(auxiliarValue);
												
						break;
					}
				}
				
			}
	
			//En caso de que no esté agregado, agregamos a la lista
			if (!this.control) {
				 
				this.view.setId(id);
				
				this.view.setValue(1);

				this.listViews.add(view);
				
			}
			
		//En caso de que no se halle la peli solicitada, se le tira este error al usuario.
		} catch (NoSuchElementException nsee) {
			
			//-> Tiene un error.
			message1.logMessage(1); 
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
		
		this.control = false;
		Optional<Movies> mo = null; 
		Integer auxiliarValue = 0, auxiliarId = -1;
		
		try {
			
			mo = Optional.ofNullable(this.listMovies.
					stream().
					filter(m -> (m.getTitle().toLowerCase()).equals(title.toLowerCase().trim())).
					findFirst().
					get());
			
			
			//Buscamos el Id de la pelicula
			for (Movies m : this.listMovies) {
				
				if ((m.getTitle().toLowerCase()).equals(title.toLowerCase().trim())) {
					
					auxiliarId = m.getId();
				}
			}
			
			if (auxiliarId != -1) {
				
				for (int i = 0; i < this.listViews.size(); i++) {
					
					if (this.listViews.get(i).getId() == auxiliarId) {
						
						this.control = true;
						
						auxiliarValue = this.listViews.get(i).getValue() + 1;
						
						this.listViews.get(i).setValue(auxiliarValue);
						
						break;
					}
					
				}
				
			} 
			
			//Agregamos en caso de que no exista a la lista de vistos
			if (auxiliarId != -1 && !this.control) {
				
				this.view.setId(auxiliarId);
				this.view.setValue(1);
				
				this.listViews.add(view);
				
			}
			
			
		} catch (NoSuchElementException nsee) {
			
			System.out.println("You're looking for an unexisting movie");
			
			System.out.print(nsee.getCause());
			
		}
		return mo;
		
	}


	//Devuelve una lista de peliculas segun clasificacion
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
			
//			this.control = true;
				
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
	
	
	//Metodo para ver las pelis con mayores views
	public List<Movies> mostViews(){
		
		List<Views> allMoviesViews = new ArrayList<>();
		List<Movies> mostViewsMovies = new ArrayList<>();
		List<Movies> theOneToReturn = new ArrayList<>();
		
		int value, auxiliar1 = 0, auxiliar2 = 0;
		
		Integer max = 0;
		
		//Buscamos el mayor 
		for (Views v : this.listViews) {
			if(v.getValue() > max) max = this.view.getValue(); 
		}
		
		//Agregamos los mayores
		for (Views v : this.listViews) {
			if (v.getValue() == max) allMoviesViews.add(v); 
		}
		
		//Pasamos las pelis más vistas
		for (Views v : allMoviesViews) {
			
			for (Movies m : this.listMovies) {
				if (v.getId() == m.getId()) mostViewsMovies.add(m);
			}
		}
		
		
		if (mostViewsMovies.size() > 3) {
			//Seleccionamos tres pelis más vistas al azar
			for (int i = 0;  i < 3; i++) {
				
				value = (int) Math.random()*mostViewsMovies.size();
				
				if (i==0) auxiliar1 = value; 
				if (i==1) auxiliar2 = value;
				
				if (i != 0) {
					
					while (value == auxiliar1 || value == auxiliar2) {
						value = (int) (Math.random()*mostViewsMovies.size()); 
					}
				}
				
				theOneToReturn.add(mostViewsMovies.get(value)); 
			}
		} else return mostViewsMovies;
		
		return theOneToReturn;	
	}
	
}
