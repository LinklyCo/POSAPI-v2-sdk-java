package com.linkly.pos.demoPos.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class WebConfig {

	@Bean
	  public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperBuilderCustomizer() {
	    return new Jackson2ObjectMapperBuilderCustomizer() {
	    
	      @Override
	      public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
	        
	        final String FORMAT = "MM/dd/yyyy hh:mm:ss a";
	        
	        jacksonObjectMapperBuilder
	            .serializers(
	                new LocalDateTimeSerializer(
	                    DateTimeFormatter.ofPattern(FORMAT)))
	            .deserializers(
	                new LocalDateTimeDeserializer(
	                    DateTimeFormatter.ofPattern(FORMAT)));
	      }
	    };
	}
}
