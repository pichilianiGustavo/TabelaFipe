package br.com.ti365.tabelaFipe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ti365.tabelaFipe.service.interfaces.DataConverter;

@Service
public class FipeDataConverter implements DataConverter{

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public <T> List<T> getData(String json, TypeReference<List<T>> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
