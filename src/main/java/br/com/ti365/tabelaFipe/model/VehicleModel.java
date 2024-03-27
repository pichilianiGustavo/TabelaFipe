package br.com.ti365.tabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleModel{

	@JsonAlias("nome")
	private String modelName;
	@JsonAlias("codigo")
	private String modelCode;

	@Override
	public String toString() {
	    return "Modelo do Veículo{" +
	            "Nome do Modelo='" + modelName + '\'' +
	            ",Código do Modelo='" + modelCode + '\'' +
	            '}';
	}
	
}
