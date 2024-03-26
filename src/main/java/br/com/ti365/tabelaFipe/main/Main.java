package br.com.ti365.tabelaFipe.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.ti365.tabelaFipe.model.Vehicle;
import br.com.ti365.tabelaFipe.properties.ParallelumApiConfigProperties;
import br.com.ti365.tabelaFipe.service.interfaces.ApiConsumer;
import br.com.ti365.tabelaFipe.service.interfaces.DataConverter;

@Controller
public class Main {

	@Autowired
	ParallelumApiConfigProperties parallelumApiConfigProperties;
	@Autowired
	private ApiConsumer fipeApiConsumer;
	@Autowired
	private DataConverter converter;
	private Scanner scanner = new Scanner(System.in);
	private static final Map<String, String> vehicleMapper;

	static {
		Hashtable<String, String> tmp = new Hashtable<String, String>();
		tmp.put("1", "Carros");
		tmp.put("2", "Motos");
		tmp.put("3", "Caminhoes");
		vehicleMapper = Collections.unmodifiableMap(tmp);
	}

	@Autowired
	public void showMenu() {

		var vehicleOption = chooseVehicleOption();
		System.out.println("\nOpção de veículos escolhida: " + vehicleMapper.get(vehicleOption));
		System.out.println("\nURL da Requisição: " + parallelumApiConfigProperties.getBase()
				+ vehicleMapper.get(vehicleOption).toLowerCase());
		var jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ vehicleMapper.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands());
		List<Vehicle> vehicleListData = new ArrayList<Vehicle>();
		vehicleListData = converter.getData(jsonResponse, new TypeReference<List<Vehicle>>() {
		});
		vehicleListData.stream().forEach(System.out::println);
		var chosenVehicle = chooseVehicleCode(vehicleListData);
		System.out.println(chosenVehicle);
		jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ vehicleMapper.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands()
				+ chosenVehicle.getCode() + parallelumApiConfigProperties.getModels());
		System.out.println(jsonResponse);
	}

	private String chooseVehicleOption() {
		System.out.println("\n##### OPÇÕES #####" + "\n 1 - Carros" + "\n 2 - Motos" + "\n 3 - Caminhões"
				+ "\n\n Escolha uma dentre as opções acima:");
		var vehicleOption = scanner.nextLine();
		while (vehicleMapper.get(vehicleOption) == null) {
			System.out.println("Opção inválida!");
			vehicleOption = chooseVehicleOption();
		}
		return vehicleOption;
	}

	private Vehicle chooseVehicleCode(List<Vehicle> vehicleList) {
	    System.out.println("\n###############################################################"
	            + "\n Digite o código do veículo que deseja: ");
	    String vehicleCodeOption = scanner.nextLine();
	    for (Vehicle vehicle : vehicleList) {
	        if (vehicle.getCode().equals(vehicleCodeOption)) {
	            return vehicle;
	        }
	    }

	    System.out.println("Veículo não encontrado!");
	    return chooseVehicleCode(vehicleList);
	}
//	TODO
//	private Vehicle chooseModelCode(List<Vehicle> vehicleList) {
//		System.out.println("\n###############################################################"
//				+ "\n Digite o código do veículo que deseja: ");
//		final var vehicleCodeOption = reader.nextLine();
//		Vehicle chosenVehicle = vehicleList.stream().filter(vehicle -> vehicle.getCode().equals(vehicleCodeOption))
//				.findFirst().orElse(null);
//		while (chosenVehicle == null) {
//			System.out.println("Veículo não encontrado! Por favor, digite novamente.");
//			chosenVehicle = chooseModelCode(vehicleList);
//		}
//		return chosenVehicle;
//	}
}
