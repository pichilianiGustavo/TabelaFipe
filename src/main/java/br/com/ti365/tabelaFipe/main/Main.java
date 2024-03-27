package br.com.ti365.tabelaFipe.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.ti365.tabelaFipe.model.Vehicle;
import br.com.ti365.tabelaFipe.model.VehicleModel;
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
		var jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ vehicleMapper.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands());
		List<Vehicle> vehicleListData = new ArrayList<Vehicle>();
		vehicleListData = converter.getListFromJsonString(jsonResponse, new TypeReference<List<Vehicle>>() {
		});
		vehicleListData.stream().forEach(System.out::println);
		var chosenVehicle = chooseVehicleCode(vehicleListData);
		jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ vehicleMapper.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands()
				+ chosenVehicle.getCode() + parallelumApiConfigProperties.getModels());
		List<VehicleModel> vehicleModelList = new ArrayList<VehicleModel>();
		vehicleModelList = converter.getListFromJsonNode(jsonResponse, "modelos", new TypeReference<List<VehicleModel>>() {});
		vehicleModelList.stream().forEach(System.out::println);
		VehicleModel chosenVehicleModel = chooseVehicleModelCode(vehicleModelList);
		System.out.println(chosenVehicleModel);
		jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ vehicleMapper.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands()
				+ chosenVehicle.getCode() + parallelumApiConfigProperties.getModels() + chosenVehicleModel.getCode() + parallelumApiConfigProperties.getYears());
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

	private <T> T chooseItem(List<T> itemList, Function<T, String> getCode, String className) {
	    System.out.println("\n###############################################################"
	            + "\n Digite o código do " + className + " que deseja: ");
	    String itemCodeOption = scanner.nextLine();
	    for (T item : itemList) {
	        if (getCode.apply(item).equals(itemCodeOption)) {
	            return item;
	        }
	    }

	    System.out.println(className + " não encontrado!");
	    return chooseItem(itemList, getCode, className);
	}

	private Vehicle chooseVehicleCode(List<Vehicle> vehicleList) {
	    return chooseItem(vehicleList, Vehicle::getCode, "Veículo");
	}

	private VehicleModel chooseVehicleModelCode(List<VehicleModel> vehicleModelList) {
	    return chooseItem(vehicleModelList, VehicleModel::getCode, "Modelo do Veículo");
	}
}
