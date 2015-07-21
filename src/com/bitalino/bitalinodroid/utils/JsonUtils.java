package com.bitalino.bitalinodroid.utils;

import com.google.gson.Gson;

public class JsonUtils {
	private static Gson gson = new Gson();

	public static String toJson(Object src) {
		return gson.toJson(src);
	}

	public static <T> T fromJson(String json, Class<T> klass) {
		return gson.fromJson(json, klass);
	}
}
