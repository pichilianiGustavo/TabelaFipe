package br.com.ti365.tabelaFipe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ti365.tabelaFipe.service.interfaces.DataConverter;

@Service
public class FipeDataConverter implements DataConverter{

	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public <T> List<T> getListFromJsonString(String json, TypeReference<List<T>> typeReference) {
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
	
	public <T> List<T> getListFromJsonNode(String json, String node, TypeReference<List<T>> typeReference) {
        try {
            JsonNode jsonNode = mapper.readTree(json);
            return mapper.convertValue(jsonNode.get(node), typeReference);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
