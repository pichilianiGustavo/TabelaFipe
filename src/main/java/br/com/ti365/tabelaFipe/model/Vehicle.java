package br.com.ti365.tabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
	
	@JsonAlias("codigo")
	protected String code;
	@JsonAlias("nome")
	private String brand;
	
	@Override
	public String toString() {
	    return "Veículo{" +
	            "Nome da Marca='" + brand + '\'' +
	            ",Código da Marca='" + code + '\'' +
	            '}';
	}
}
