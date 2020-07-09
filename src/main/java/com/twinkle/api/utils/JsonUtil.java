package com.twinkle.api.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

/**
 * @author cuonglv
 *
 */
@Log4j2
public class JsonUtil {
	public static <T> T convertJsonStringToObject(String jsonContent, Class<T> clazz) {
		T em = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			em = mapper.readValue(jsonContent, clazz);
		} catch (IOException e) {
			log.info(e.getLocalizedMessage(), e);
		}

		return em;
	}

	public static String toJsonString(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(object).toString();
		} catch (JsonProcessingException e) {
			log.info(e.getLocalizedMessage(), e);
		}
		return json;
	}
}
