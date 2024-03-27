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
import br.com.ti365.tabelaFipe.model.VehicleData;
import br.com.ti365.tabelaFipe.model.VehicleModel;
import br.com.ti365.tabelaFipe.properties.ParallelumApiConfigProperties;
import br.com.ti365.tabelaFipe.service.interfaces.ApiConsumer;
import br.com.ti365.tabelaFipe.service.interfaces.DataConverter;
import br.com.ti365.tabelaFipe.service.util.ServiceUtil;

@Controller
public class Main {

	@Autowired
	ParallelumApiConfigProperties parallelumApiConfigProperties;
	@Autowired
	private ApiConsumer fipeApiConsumer;
	@Autowired
	private DataConverter converter;
	private Scanner scanner = new Scanner(System.in);
	private static final Map<String, String> VEHICLE_MAPPER;

	static {
		Hashtable<String, String> tmp = new Hashtable<String, String>();
		tmp.put("1", "Carros");
		tmp.put("2", "Motos");
		tmp.put("3", "Caminhoes");
		VEHICLE_MAPPER = Collections.unmodifiableMap(tmp);
	}

	@Autowired
	public void showMenu() {

		var vehicleOption = chooseVehicleOption();
		var jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ VEHICLE_MAPPER.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands());
		List<Vehicle> vehicleListData = new ArrayList<Vehicle>();
		vehicleListData = converter.getListFromJsonString(jsonResponse, new TypeReference<List<Vehicle>>() {
		});
		vehicleListData.stream().forEach(System.out::println);
		var chosenVehicle = chooseVehicleCode(vehicleListData);
		jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ VEHICLE_MAPPER.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands()
				+ chosenVehicle.getBrandCode() + parallelumApiConfigProperties.getModels());
		List<VehicleModel> vehicleModelList = new ArrayList<VehicleModel>();
		vehicleModelList = converter.getListFromJsonNode(jsonResponse, "modelos",
				new TypeReference<List<VehicleModel>>() {
				});
		vehicleModelList.stream().forEach(System.out::println);
		VehicleModel chosenVehicleModel = chooseVehicleModelCode(vehicleModelList);
		System.out.println(chosenVehicleModel);
		jsonResponse = fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
				+ VEHICLE_MAPPER.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands()
				+ chosenVehicle.getBrandCode() + parallelumApiConfigProperties.getModels()
				+ chosenVehicleModel.getModelCode() + parallelumApiConfigProperties.getYears());
		System.out.println(jsonResponse);
		List<VehicleData> vehicleYearDataList = new ArrayList<VehicleData>();
		vehicleYearDataList = converter.getListFromJsonString(jsonResponse, new TypeReference<List<VehicleData>>() {
		});
		List<VehicleData> vehicleDataList = new ArrayList<VehicleData>();
		for (int i = 1; i < vehicleYearDataList.size(); i++) {
			var jsonAux= fipeApiConsumer.getApiData(parallelumApiConfigProperties.getBase()
					+ VEHICLE_MAPPER.get(vehicleOption).toLowerCase() + parallelumApiConfigProperties.getBrands()
					+ chosenVehicle.getBrandCode() + parallelumApiConfigProperties.getModels()
					+ chosenVehicleModel.getModelCode() + parallelumApiConfigProperties.getYears() + vehicleYearDataList.get(i).getVehicleYear());
			System.out.println(jsonAux);
			VehicleData vehicleData = converter.getObjectFromJsonString(jsonAux, VehicleData.class);
			vehicleDataList.add(vehicleData);
		}
		System.out.println(vehicleDataList);
	}

	private String chooseVehicleOption() {
		System.out.println("\n##### OPÇÕES #####" + "\n 1 - Carros" + "\n 2 - Motos" + "\n 3 - Caminhões"
				+ "\n\n Escolha uma dentre as opções acima:");
		var vehicleOption = scanner.nextLine();
		while (VEHICLE_MAPPER.get(vehicleOption) == null) {
			System.out.println("Opção inválida!");
			vehicleOption = chooseVehicleOption();
		}
		return vehicleOption;
	}

	private Vehicle chooseVehicleCode(List<Vehicle> vehicleList) {
		return ServiceUtil.getItemInListByCode(vehicleList, Vehicle::getBrandCode, "Veículo", scanner);
	}

	private VehicleModel chooseVehicleModelCode(List<VehicleModel> vehicleModelList) {
		return ServiceUtil.getItemInListByCode(vehicleModelList, VehicleModel::getModelCode, "Modelo do Veículo",
				scanner);
	}
}
