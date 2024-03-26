package br.com.ti365.tabelaFipe.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

public interface DataConverter {

	<T> List<T> getData(String json, TypeReference<List<T>> typeReference);
}
