package com.twinkle.api.common;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author cuonglv
 *
 */
public class CustomDateDeserializer extends StdDeserializer<Date>{

	public CustomDateDeserializer() {
		this(null);
	}

	public CustomDateDeserializer(Class t) {
		super(t);
	}

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		System.out.println(jsonparser.getValueAsLong());
		Date date = null;
		try {
			if(jsonparser.getValueAsLong() != 0) {
				date = new Date(jsonparser.getValueAsLong());
			} else {
				date = CustomDateSerializer.formater.parse(jsonparser.getText());
			}
			return date;
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }

	}

}
