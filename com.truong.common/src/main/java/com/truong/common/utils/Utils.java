package com.truong.common.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

public class Utils {
	

	private Utils() {
	}
	
	public static <T> List<T> convertJsonStringToListObject(String jsonString, Class<T[]> objectclass)
			throws Exception {
		jsonString = Strings.isNullOrEmpty(jsonString) ? "[]" : jsonString;
		ObjectMapper mapper = new ObjectMapper();
		List<T> result = Arrays.asList(mapper.readValue(jsonString, objectclass));
		return result;
	}
	
	public static <T> T convertJsonStringToObject(String jsonString, Class<T> objectclass) throws Exception {
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.readValue(jsonString, objectclass);
		return result;
	}
	
	public static <T> String convertObjectToJsonString(Object data) throws Exception {

		if (data == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(data);
		return result;
	}
	
	public static String getDatetimeString(Date date) {
		if (date == null) {
			return "";
		} else {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
		}
	}

}
