package br.com.ti365.tabelaFipe.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "parallelum", ignoreUnknownFields = false)
public class ParallelumApiConfigProperties {

	private String base;
	private String brands;
	private String models;
	private String years;
	
	
}
