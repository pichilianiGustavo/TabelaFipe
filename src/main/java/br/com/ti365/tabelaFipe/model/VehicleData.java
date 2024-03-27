package br.com.ti365.tabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleData {

	@JsonAlias("codigo")
	private String vehicleYear;
	@JsonAlias("nome")
	private String vehicleYearName;
	@JsonAlias("Valor")
	private String vehicleValue;
	@JsonAlias("Marca")
	private String vehiclebrand;
	@JsonAlias("Modelo")
	private String vehicleModel;
	@JsonAlias("AnoModelo")
	private String vehicleModelYear;
	@JsonAlias("Combustivel")
	private String vehicleFuel;
	@JsonAlias("CodigoFipe")
	private String vehicleFipeCode;

	@Override
	public String toString() {
		return "Veículo{" + "Nome da Marca='" + vehiclebrand + '\'' + ",Nome do Modelo='" + vehicleModel + '\''
				+ "Codigo Fipe='" + vehicleFipeCode + '\'' + ",Valor='" + vehicleValue + '\''
				+ "Ano do Modelo='" + vehicleModelYear + '\'' + ",Tipo de Combustível='" + vehicleFuel + '\'' + '}';
	}

}
