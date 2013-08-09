package com.boneis.support.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.springframework.stereotype.Component;

@Component
public class Config {

	private static CompositeConfiguration configuration;
	
	public void setConfiguration(CompositeConfiguration configuration) {
		Config.configuration = configuration;
	}

	public static String get(String k){
		return configuration.getString(k);
	}
	
}
