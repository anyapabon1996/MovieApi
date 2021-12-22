package com.example.demo.model;

import java.util.Comparator;

public class ComparatorMovies implements Comparator<Movies> {
	
	//Acomoda la lista de modo creciente 
	@Override
	public int compare(Movies m1, Movies m2) {
		if (m1.getId() < (m2.getId())) {
			return -1;
		} else return 0;
	}
	
	
}
