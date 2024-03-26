package br.com.ti365.tabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
	
	@JsonAlias("codigo")
	private String code;
	@JsonAlias("nome")
	private String brand;
}
