package com.example.demo.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor  @NoArgsConstructor 
public class Movies implements Serializable {
	
	private @Setter @Getter Integer id;
	private @Setter @Getter String title; 
	private @Setter @Getter double qualification;
	private @Setter @Getter boolean adultsOnly; 
	private @Setter @Getter Gender gender; 
	private @Setter @Getter Classification classification; 
	private @Setter @Getter Integer premiere; 
	
	
	//Esto es porque no aparece el metodo getAdultsOnly con lombok
//	public boolean getAdultsOnly() {
//		return this.adultsOnly;
//	}

}
