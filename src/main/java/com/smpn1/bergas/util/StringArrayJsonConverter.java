package com.smpn1.bergas.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class StringArrayJsonConverter implements AttributeConverter<String[], String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        try {
            return objectMapper.writeValueAsString(attribute); // Convert to JSON string
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting String[] to JSON", e);
        }
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, String[].class); // Convert JSON string back to array
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to String[]", e);
        }
    }
}
